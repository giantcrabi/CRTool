package com.kreators.crtoolv1.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by DELL on 15/06/2016.
 */
public class SalesOutScanFragment extends Fragment {

//    private ImageView mImageview;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_sales_out_scan, container, false);
//
//        mImageview = (ImageView) view.findViewById(R.id.imageSN);
//
//        Bitmap imageBitmap = (Bitmap) getArguments().get("data");
//        mImageview.setImageBitmap(imageBitmap);
//
//        return view;
//    }

    private ZBarScannerView mScannerView;
    public SalesOutScanFragment(Context context) {
        mScannerView = new ZBarScannerView(context);
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

    public ZBarScannerView getmScannerView() {
        return mScannerView;
    }

}
