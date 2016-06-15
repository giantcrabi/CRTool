package com.kreators.crtoolv1.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kreators.crtoolv1.R;
import com.kreators.crtoolv1.Fragment.SalesOutInputFragment;
import com.kreators.crtoolv1.Fragment.SalesOutMainFragment;
import com.kreators.crtoolv1.Fragment.SalesOutScanFragment;

public class SalesOutActivity extends AppCompatActivity implements SalesOutMainFragment.SalesOutMainListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

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
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.sales_out_activity, salesOutMainFragment).commit();
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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            SalesOutScanFragment salesOutScanFragment = new SalesOutScanFragment();

            Bundle extras = data.getExtras();

            salesOutScanFragment.setArguments(extras);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.sales_out_activity, salesOutScanFragment);
            transaction.addToBackStack(null);

            transaction.commitAllowingStateLoss();
        }
    }
}
