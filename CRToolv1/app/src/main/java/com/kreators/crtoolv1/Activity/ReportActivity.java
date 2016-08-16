package com.kreators.crtoolv1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.ReportMainFragment;
import com.kreators.crtoolv1.Fragment.ReportTrackRecordFragment;
import com.kreators.crtoolv1.Model.DateParameter;
import com.kreators.crtoolv1.R;

public class ReportActivity extends AppCompatActivity implements ReportMainFragment.ReportMainListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Report");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        if (findViewById(R.id.reportActivity) != null) {
            if (savedInstanceState != null) {
                return;
            }
            ReportMainFragment reportMainFragment= new ReportMainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.reportActivity, reportMainFragment).commit();
        }
    }

    public void onReportTrackRecordButtonClick(String dateFrom, String dateTo) {
        ReportTrackRecordFragment reportTrackRecordFragment =  new ReportTrackRecordFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reportActivity, reportTrackRecordFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onReportSalesOutButtonClick(String dateFrom, String dateTo) {
        Intent intent = new Intent(this, ReportSalesOutActivity.class);
        DateParameter dateArgs = new DateParameter(dateFrom,dateTo);
        intent.putExtra(Protocol.DATE_PARAMETER,dateArgs);
        startActivity(intent);
    }

}
