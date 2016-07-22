package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kreators.crtoolv1.Fragment.SalesOutInputFragment;
import com.kreators.crtoolv1.Fragment.SalesOutMainFragment;
import com.kreators.crtoolv1.Fragment.SalesOutScanFragment;
import com.kreators.crtoolv1.R;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class SalesOutActivity extends AppCompatActivity implements SalesOutMainFragment.SalesOutMainListener,
        ZBarScannerView.ResultHandler {

    //static final int REQUEST_IMAGE_CAPTURE = 1;

    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_out);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.sales_out_activity) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            SalesOutMainFragment salesOutMainFragment = new SalesOutMainFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.sales_out_activity, salesOutMainFragment).commit();
        }

    }

    public void onInputButtonClick() {
        SalesOutInputFragment salesOutInputFragment = new SalesOutInputFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.sales_out_activity, salesOutInputFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void onScanButtonClick() {
        SalesOutScanFragment salesOutScanFragment = new SalesOutScanFragment(this);

        mScannerView = salesOutScanFragment.getmScannerView();
        mScannerView.setResultHandler(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.sales_out_activity, salesOutScanFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        if(rawResult.getContents() == null){
            Toast.makeText(this, "Error No Contents", Toast.LENGTH_LONG).show();
        } else {
            String scanContent = rawResult.getContents();
            //String scanFormat = rawResult.getBarcodeFormat().getName();

            SalesOutInputFragment salesOutInputFragment = new SalesOutInputFragment();

            Bundle b = new Bundle();
            b.putString("Content", scanContent);
            salesOutInputFragment.setArguments(b);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.sales_out_activity, salesOutInputFragment, "INPUT");

            transaction.commit();
        }

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }

//    public void onScanButtonClick() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            SalesOutScanFragment salesOutScanFragment = new SalesOutScanFragment();
//
//            Bundle extras = data.getExtras();
//
//            salesOutScanFragment.setArguments(extras);
//
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//            transaction.replace(R.id.sales_out_activity, salesOutScanFragment);
//            transaction.addToBackStack(null);
//
//            transaction.commitAllowingStateLoss();
//        }
//    }
}
