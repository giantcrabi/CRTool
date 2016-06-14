package com.kreators.crtoolv1;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class SalesOutActivity extends FragmentActivity implements SalesOutMainFragment.SalesOutMainListener {

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

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            salesOutMainFragment.setArguments(getIntent().getExtras());

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
}
