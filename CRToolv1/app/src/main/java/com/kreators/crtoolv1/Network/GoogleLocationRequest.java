package com.kreators.crtoolv1.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.kreators.crtoolv1.Commons.Constant;

/**
 * Created by DELL on 08/08/2016.
 */
public class GoogleLocationRequest implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private Context mContext;
    private GoogleLocationListener mListener;
    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;

    private ProgressDialog locRequestProgress;
    private Handler pdCanceller;
    private Runnable progressRunnable;

    public GoogleLocationRequest(Context context) {

        mContext = context;

        googleApiClient = new GoogleApiClient.Builder(context).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(10) // 10 meters
                .setInterval(60 * 1000)        // 60 seconds, in milliseconds
                .setFastestInterval(10 * 1000); // 10 second, in milliseconds

        locRequestProgress = new ProgressDialog(context);
        locRequestProgress.setTitle(Constant.searchDialog);
        locRequestProgress.setMessage(Constant.msgDialog);
        locRequestProgress.setCancelable(true);
        locRequestProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(mContext, Constant.noLocation, Toast.LENGTH_SHORT).show();
                removeLocationUpdates();
            }
        });

        pdCanceller = new Handler();
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                locRequestProgress.cancel();
            }
        };
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mListener != null) {
            mListener.onConnected(this);
            checkLocationSettings();
        }
    }

    @Override
    public void onConnectionSuspended(int errorCode) {
        if (mListener != null) {
            String errorMessage = Constant.locSuspend;
            mListener.onConnectionSuspended(this, errorMessage);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mListener != null) {
            int errorCode = connectionResult.getErrorCode();
            String errorMessage = Constant.locFailed;
            if(errorCode == 2){
                errorMessage = Constant.updateServices;
            }
            mListener.onConnectionFailed(this, errorMessage);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (locRequestProgress != null) {
            locRequestProgress.dismiss();
            pdCanceller.removeCallbacks(progressRunnable);
        }
        if (mListener != null) {
           mListener.onLocationChanged(this, location);
        }
    }

    public void setListener(GoogleLocationListener listener) { mListener = listener; }

    public void setGoogleAPIConnection(boolean connect) {
        if (connect) {
            googleApiClient.connect();
        } else {
            googleApiClient.disconnect();
        }
    }

    public boolean getConnectionStatus() {
        return googleApiClient.isConnected();
    }

    public void checkLocationSettings(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult((Activity) mContext, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    public void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //lastLocation equivalent to user's current location
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            if(lastLocation == null){
                locRequestProgress.show();
                pdCanceller.postDelayed(progressRunnable, 10000);

                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
            } else {
                if (mListener != null) {
                    mListener.onLocationChanged(this, lastLocation);
                }
            }
        }
    }

    public void removeLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }
}
