package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kreators.crtoolv1.Fragment.ReportHistoryFragment;
import com.kreators.crtoolv1.Fragment.ReportMainFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutFragment;
import com.kreators.crtoolv1.Fragment.ReportTrackRecordFragment;
import com.kreators.crtoolv1.R;

public class ReportActivity extends AppCompatActivity implements ReportMainFragment.ReportMainListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.reportActivity) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ReportMainFragment reportMainFragment= new ReportMainFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.reportActivity, reportMainFragment).commit();
        }
    }

    public void onReportTrackRecordButtonClick() {
        ReportTrackRecordFragment reportTrackRecordFragment =  new ReportTrackRecordFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reportActivity, reportTrackRecordFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onReportHistoryButtonClick() {
        ReportHistoryFragment reportHistoryFragment =  new ReportHistoryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reportActivity, reportHistoryFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onReportSalesOutButtonClick() {
        ReportSalesOutFragment reportSalesOutFragment =  new ReportSalesOutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reportActivity, reportSalesOutFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
