package com.kreators.crtoolv1.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.kreators.crtoolv1.Fragment.Dialog.SelectOutletDialogFragment;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        SelectOutletDialogFragment.MyDialogFragmentListener {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private static final int PERMISSION_LOCATION_REQUEST_CODE = 1;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    private final static int EARTH_RADIUS = 6371;
    private final static double CHECK_IN_RADIUS = 0.5;

    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;
    private double curLat;
    private double curLon;

    private List<Pair<Pair<Double,Double>,String>> listOutlet;

    private ProgressDialog pd;
    private ProgressDialog locRequestProgress;

    private Handler pdCanceller;
    private Runnable progressRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_LOCATION_REQUEST_CODE);
        }

        googleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(10) // 10 meters
                .setInterval(60 * 1000)        // 60 seconds, in milliseconds
                .setFastestInterval(10 * 1000); // 10 second, in milliseconds

        listOutlet = new ArrayList<Pair<Pair<Double,Double>,String>>();

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);

        locRequestProgress = new ProgressDialog(this);
        locRequestProgress.setTitle("Searching...");
        locRequestProgress.setMessage("Please wait.");
        locRequestProgress.setCancelable(true);
        locRequestProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "Cannot find your location", Toast.LENGTH_SHORT).show();
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            removeLocationUpdates();
            googleApiClient.disconnect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        //checkLocationSettings();
                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (locRequestProgress != null) {
            locRequestProgress.dismiss();
            pdCanceller.removeCallbacks(progressRunnable);
        }
        storeCurrentLocation(location);
        searchNearestOutlet();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (pd != null) {
            pd.dismiss();
        }
        checkLocationSettings();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onReturnValue(String loc) {
        Log.d("CHOOSEN OUTLET: ", loc);
        Intent intent = new Intent(this, SalesOutActivity.class);
        startActivity(intent);
    }

    private void checkLocationSettings(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient
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
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //lastLocation equivalent to user's current location
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            //LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);

            if(lastLocation == null){
                locRequestProgress.show();
                pdCanceller.postDelayed(progressRunnable, 10000);

                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
            } else {
                storeCurrentLocation(lastLocation);
                searchNearestOutlet();
            }
        }
    }

    private void removeLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    private void storeCurrentLocation(Location location) {
        curLat = location.getLatitude();
        curLon = location.getLongitude();

        //Dummy data to trigger dialog
        listOutlet.clear();
        listOutlet.add(new Pair<Pair<Double,Double>,String>(new Pair<Double,Double>((curLat + 0.000001), (curLon + 0.000001)), "Outlet A"));
        listOutlet.add(new Pair<Pair<Double,Double>,String>(new Pair<Double,Double>((curLat - 0.000001), (curLon - 0.000001)), "Outlet B"));
    }

    private void searchNearestOutlet() {
        List<String> nearestOutlet= new ArrayList<String>();
        pd.setTitle("Searching...");
        pd.show();
        for(int i = 0; i < listOutlet.size(); i++){
            double distance = greatCircleDistance(curLat, curLon, listOutlet.get(i).first.first, listOutlet.get(i).first.second);
            Log.v("Contains: ", "lat: " + listOutlet.get(i).first.first + ", lon: " + listOutlet.get(i).first.second);
            if(distance <= CHECK_IN_RADIUS){
                nearestOutlet.add(listOutlet.get(i).second);
            }
        }
        if (pd != null) {
            pd.dismiss();
        }
        if(nearestOutlet.size() > 0) {
            if(nearestOutlet.size() > 1){
                showDialog(nearestOutlet);
            }
            else{
                Log.d("CHOOSEN OUTLET: ", nearestOutlet.get(0).toString());
                Intent intent = new Intent(this, SalesOutActivity.class);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this, "There aren't any outlets nearby", Toast.LENGTH_LONG).show();
        }
    }

    private void showDialog(List<String> nearestOutlet) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("Select Outlet");
        if (prev != null) {
            ft.remove(prev);
        }

        // Create and show the dialog.
        SelectOutletDialogFragment SO = SelectOutletDialogFragment.newInstance(nearestOutlet);
        SO.show(ft,"Select Outlet");
    }

    public void checkIn(View view) {
        if (googleApiClient != null && !googleApiClient.isConnected()) {
            pd.setTitle("Connecting...");
            pd.show();
            googleApiClient.connect();
        }
        if (googleApiClient != null && googleApiClient.isConnected()) {
            checkLocationSettings();
        }
    }

    public void viewReport(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    private static double greatCircleDistance(double latNow, double lonNow, double lat, double lon){
        double latDistance = toRad(lat-latNow);
        double lonDistance = toRad(lon-lonNow);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(latNow)) * Math.cos(toRad(lat)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS * c;
    }

    private static double toRad(double value) {
        return value * Math.PI / 180;
    }

}
