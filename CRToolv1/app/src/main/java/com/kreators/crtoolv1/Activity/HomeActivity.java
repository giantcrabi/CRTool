package com.kreators.crtoolv1.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kreators.crtoolv1.Commons.Url;
import com.kreators.crtoolv1.Fragment.Dialog.SelectOutletDialogFragment;
import com.kreators.crtoolv1.Network.GetVolleyRequest;
import com.kreators.crtoolv1.Network.GoogleLocationListener;
import com.kreators.crtoolv1.Network.GoogleLocationRequest;
import com.kreators.crtoolv1.Network.VolleyListener;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.Network.VolleyRequest;
import com.kreators.crtoolv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SelectOutletDialogFragment.MyDialogFragmentListener {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private static final int PERMISSION_LOCATION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private VolleyManager volleyManager;
    private GoogleLocationRequest googleLocationRequest;

    private double curLat;
    private double curLon;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_LOCATION_REQUEST_CODE);
        }

        googleLocationRequest = new GoogleLocationRequest(this);
        googleLocationRequest.setListener(new GoogleLocationListener() {
            @Override
            public void onConnected(GoogleLocationRequest request) {
                if (pd != null) {
                    pd.dismiss();
                }
            }

            @Override
            public void onConnectionSuspended(GoogleLocationRequest request, String errorMessage) {
                if (pd != null) {
                    pd.dismiss();
                }
                Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectionFailed(GoogleLocationRequest request, String errorMessage) {
                if (pd != null) {
                    pd.dismiss();
                }
                Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLocationChanged(GoogleLocationRequest request, Location location) {
                storeCurrentLocation(location);
                searchNearestOutlet();
            }
        });

        volleyManager = VolleyManager.getInstance(getApplicationContext());

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleLocationRequest.getConnectionStatus()) {
            googleLocationRequest.removeLocationUpdates();
            googleLocationRequest.setGoogleAPIConnection(false);
        }
        volleyManager.cancelPendingRequests("GET");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        googleLocationRequest.startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        //checkLocationSettings();
                        break;
                }
                break;
        }
    }

    @Override
    public void onReturnValue(String loc) {
        Intent intent = new Intent(this, SalesOutActivity.class);
        intent.putExtra("choosenOutlet", loc);
        startActivity(intent);
    }

    private void storeCurrentLocation(Location location) {
        curLat = location.getLatitude();
        curLon = location.getLongitude();
    }

    private void searchNearestOutlet() {
        pd.setTitle("Searching...");
        pd.show();

        GetVolleyRequest request = new GetVolleyRequest(Url.CHECK_IN_OUTLET);
        request.putParams("lon", String.valueOf(curLon));
        request.putParams("lat", String.valueOf(curLat));
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                Log.e(TAG, result.toString());
                try {
                    List<String> nearestOutlet= new ArrayList<String>();
                    JSONObject outletObj;
                    String outletName;

                    for(int i = 0; i < result.length(); i++) {
                        outletObj = result.getJSONObject(i);
                        if(outletObj.has("status")) {
                            break;
                        }
                        outletName = outletObj.getString("Name");
                        nearestOutlet.add(outletName);
                    }

                    if (pd != null) {
                        pd.dismiss();
                    }

                    if(nearestOutlet.size() > 0) {
                        if(nearestOutlet.size() > 1){
                            showDialog(nearestOutlet);
                        }
                        else{
                            Intent intent = new Intent(HomeActivity.this, SalesOutActivity.class);
                            intent.putExtra("choosenOutlet", nearestOutlet.get(0).toString());
                            startActivity(intent);
                        }
                    }
                    else {
                        Toast.makeText(HomeActivity.this, "There aren't any outlets nearby", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyRequest request, String errorMessage) {
                Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                if (pd != null) {
                    pd.dismiss();
                }
            }
        });
        volleyManager.createRequest(request, "GET");
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
        if (googleLocationRequest.getConnectionStatus()) {
            googleLocationRequest.checkLocationSettings();
        } else {
            pd.setTitle("Connecting...");
            pd.show();
            googleLocationRequest.setGoogleAPIConnection(true);
        }
    }

    public void viewReport(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setCancelable(false);
        builder.setMessage("Apakah anda ingin keluar dari aplikasi?");
        builder.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //HomeActivity.super.onBackPressed();
                        HomeActivity.this.finish();
                    }
                });
        builder.setNegativeButton("TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
