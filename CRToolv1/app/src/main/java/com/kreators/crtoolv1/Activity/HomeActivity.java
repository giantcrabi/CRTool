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
import android.view.View;
import android.widget.Toast;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Commons.SessionManager;
import com.kreators.crtoolv1.Commons.Url;
import com.kreators.crtoolv1.Fragment.Dialog.SelectOutletDialogFragment;
import com.kreators.crtoolv1.Model.Outlet;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SelectOutletDialogFragment.MyDialogFragmentListener {
    public static final String TAG = HomeActivity.class.getSimpleName();
    private static final int PERMISSION_LOCATION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private VolleyManager volleyManager;
    private GoogleLocationRequest googleLocationRequest;
    private double curLat;
    private double curLng;
    private ProgressDialog pd;
    private SimpleDateFormat sdf;
    private SessionManager sessionManager;
    private HashMap<String, String> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        setUpGoogleLocationRequest();
        initialization();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleLocationRequest.getConnectionStatus()) {
            googleLocationRequest.removeLocationUpdates();
            googleLocationRequest.setGoogleAPIConnection(false);
        }
        volleyManager.cancelPendingRequests(Protocol.GET);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, Constant.needLocation, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        googleLocationRequest.startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    @Override
    public void onReturnValue(final Outlet outlet) {
        pd.setTitle(Constant.checkinDialog);
        pd.show();
        Date dt = new Date();
        String currentTime = sdf.format(dt);
        GetVolleyRequest request = new GetVolleyRequest(Url.POST_CHECK_IN_OUTLET);
        request.putParams(Protocol.CRID, user.get(Protocol.USERID));
        request.putParams(Protocol.OUTLETID, String.valueOf(outlet.getOutletID()));
        request.putParams(Protocol.CUR_DATE_TIME, currentTime);
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                try {
                    JSONObject jsonObject = result.getJSONObject(0);

                    if (pd != null) {
                        pd.dismiss();
                    }

                    if (jsonObject.getBoolean("status")) {
                        Intent intent = new Intent(HomeActivity.this, SalesOutActivity.class);
                        intent.putExtra(Protocol.OUTLETID, String.valueOf(outlet.getOutletID()));
                        intent.putExtra(Protocol.SN_OUTLET_NAME, outlet.getOutletName());
                        startActivity(intent);
                    } else {
                        Toast.makeText(HomeActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
        volleyManager.createRequest(request, Protocol.POST);
    }

    private void storeCurrentLocation(Location location) {
        curLat = location.getLatitude();
        curLng = location.getLongitude();
    }

    private void searchNearestOutlet() {
        pd.setTitle(Constant.searchDialog);
        pd.show();
        GetVolleyRequest request = new GetVolleyRequest(Url.GET_CHECK_IN_OUTLET);
        request.putParams(Protocol.LONGITUDE, String.valueOf(curLng));
        request.putParams(Protocol.LATITUDE, String.valueOf(curLat));
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                try {
                    List<Outlet> nearestOutlet= new ArrayList<>();
                    JSONObject outletObj;

                    for(int i = 0; i < result.length(); i++) {
                        outletObj = result.getJSONObject(i);
                        if(outletObj.has("status")) {
                            break;
                        }

                        Outlet outlet = new Outlet();
                        outlet.setOutletID(outletObj.getInt("ID"));
                        outlet.setOutletName(outletObj.getString("Name"));
                        nearestOutlet.add(outlet);
                    }

                    if (pd != null) {
                        pd.dismiss();
                    }

                    if(nearestOutlet.size() > 0) {
                        if(nearestOutlet.size() > 1){
                            showDialog(nearestOutlet);
                        }
                        else{
                            onReturnValue(nearestOutlet.get(0));
                        }
                    }
                    else {
                        Toast.makeText(HomeActivity.this, Constant.noOutlets, Toast.LENGTH_LONG).show();
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
        volleyManager.createRequest(request, Protocol.GET);
    }

    private void showDialog(List<Outlet> nearestOutlet) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("Select Outlet");
        if (prev != null) {
            ft.remove(prev);
        }
        SelectOutletDialogFragment SO = SelectOutletDialogFragment.newInstance(nearestOutlet);
        SO.show(ft,"Select Outlet");
    }

    private void initialization(){
        volleyManager = VolleyManager.getInstance(getApplicationContext());

        pd = new ProgressDialog(this);
        pd.setMessage(Constant.msgDialog);
        pd.setCancelable(false);
        pd.setIndeterminate(true);

        sdf = new SimpleDateFormat(Constant.SYSTEM_DATE_COMPLETE);

        sessionManager = new SessionManager(getApplicationContext());
        user = sessionManager.getUserDetails();
    }

    private void setUpGoogleLocationRequest() {

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
    }

    public void checkIn(View view) {
        if (googleLocationRequest.getConnectionStatus()) {
            googleLocationRequest.checkLocationSettings();
        } else {
            pd.setTitle(Constant.connectDialog);
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
        builder.setMessage(Constant.exitApp);
        builder.setPositiveButton(Constant.YES,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        HomeActivity.this.finish();
                    }
                });
        builder.setNegativeButton(Constant.NO,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}