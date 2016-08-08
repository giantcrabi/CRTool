package com.kreators.crtoolv1.Network;

import android.location.Location;

/**
 * Created by DELL on 08/08/2016.
 */
public interface GoogleLocationListener {
    void onConnected(GoogleLocationRequest request);
    void onConnectionSuspended(GoogleLocationRequest request, String errorMessage);
    void onConnectionFailed(GoogleLocationRequest request, String errorMessage);
    void onLocationChanged(GoogleLocationRequest request, Location location);
}
