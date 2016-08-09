package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.kreators.crtoolv1.Fragment.Listener.SalesOutListener;
import com.kreators.crtoolv1.Fragment.SalesOutInputFragment;
import com.kreators.crtoolv1.Fragment.SalesOutScanFragment;
import com.kreators.crtoolv1.R;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class SalesOutActivity extends AppCompatActivity implements SalesOutListener {

    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_out);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            SalesOutInputFragment salesOutInputFragment = new SalesOutInputFragment();

            String curOutlet = getIntent().getStringExtra("choosenOutlet");

            if(curOutlet != null){
                setTitle(curOutlet);
            }

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.sales_out_activity, salesOutInputFragment).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onScanButtonClick() {
        SalesOutScanFragment salesOutScanFragment = new SalesOutScanFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.sales_out_activity, salesOutScanFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void handleResult(Result rawResult) {
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

            transaction.replace(R.id.sales_out_activity, salesOutInputFragment);

            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        SalesOutActivity.this.finish();
    }
}
