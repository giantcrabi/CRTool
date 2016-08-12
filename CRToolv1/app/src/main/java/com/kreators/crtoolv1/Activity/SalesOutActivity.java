package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.Listener.SalesOutListener;
import com.kreators.crtoolv1.Fragment.SalesOutInputFragment;
import com.kreators.crtoolv1.Fragment.SalesOutScanFragment;
import com.kreators.crtoolv1.R;

import me.dm7.barcodescanner.zbar.Result;

public class SalesOutActivity extends AppCompatActivity implements SalesOutListener {

    private String curOutletID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_out);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.sales_out_activity) != null) {

            if (savedInstanceState != null) {
                return;
            }

            SalesOutInputFragment salesOutInputFragment = new SalesOutInputFragment();

            curOutletID = getIntent().getStringExtra(Protocol.OUTLETID);
            String curOutletName = getIntent().getStringExtra(Protocol.OUTLETNAME);

            if(curOutletID != null && curOutletName != null){
                setTitle(curOutletName);

                Bundle b = new Bundle();
                b.putString(Protocol.OUTLETID, curOutletID);
                salesOutInputFragment.setArguments(b);
            }

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
            Toast.makeText(this, Constant.noContents, Toast.LENGTH_LONG).show();
        } else {
            String scanContent = rawResult.getContents();

            getSupportFragmentManager().popBackStack();

            SalesOutInputFragment salesOutInputFragment = new SalesOutInputFragment();

            Bundle b = new Bundle();
            b.putString(Protocol.CONTENT, scanContent);
            b.putString(Protocol.OUTLETID, curOutletID);
            salesOutInputFragment.setArguments(b);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.sales_out_activity, salesOutInputFragment);
            transaction.commit();
        }
    }
}
