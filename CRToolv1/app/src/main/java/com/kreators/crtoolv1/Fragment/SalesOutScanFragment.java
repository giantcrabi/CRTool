package com.kreators.crtoolv1.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreators.crtoolv1.Fragment.Listener.SalesOutListener;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by DELL on 15/06/2016.
 */
public class SalesOutScanFragment extends Fragment implements ZBarScannerView.ResultHandler {

    private SalesOutListener activityCallback;
    private ZBarScannerView mScannerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SalesOutListener) {
            activityCallback = (SalesOutListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SalesOutListener");
        }
        mScannerView = new ZBarScannerView(context);
        mScannerView.setResultHandler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return mScannerView;                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        activityCallback.handleResult(result);
    }
}
