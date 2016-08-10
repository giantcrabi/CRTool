package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.HistorySalesOutFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByDateFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByOutletFragment;
import com.kreators.crtoolv1.Fragment.SerialNumberSalesOutFragment;
import com.kreators.crtoolv1.R;

public class DetailReportSalesOutActivity extends AppCompatActivity implements ReportSalesOutByOutletFragment.ReportSalesOutByOutletListener,ReportSalesOutByDateFragment.ReportSalesOutByDateListener,HistorySalesOutFragment.HistorySalesOutListener {

    private String fragmentToInflate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report_sales_out);
        if (findViewById(R.id.detailReportSalesOutMainContent) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Bundle args = getIntent().getExtras();
            if(args != null) fragmentToInflate = (String) args.getSerializable(Protocol.FRAGMENT_TAG);
            if(fragmentToInflate.equals(Constant.inflateFragmentByDate)) {
                ReportSalesOutByDateFragment reportSalesOutByDateFragment = new ReportSalesOutByDateFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.detailReportSalesOutMainContent, reportSalesOutByDateFragment).commit();
            } else if (fragmentToInflate.equals(Constant.inflateFragmentByOutlet)) {
                ReportSalesOutByOutletFragment reportSalesOutByOutletFragment = new ReportSalesOutByOutletFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.detailReportSalesOutMainContent, reportSalesOutByOutletFragment).commit();
            }

        }
    }

    @Override
    public void adapterSalesOutByDateButtonClick() {
        HistorySalesOutFragment historySalesOutFragment=  new HistorySalesOutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detailReportSalesOutMainContent, historySalesOutFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void adapterSalesOutByOutletButtonClick() {
        HistorySalesOutFragment historySalesOutFragment=  new HistorySalesOutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detailReportSalesOutMainContent, historySalesOutFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void adapterHistorySalesOutButtonClick() {
        SerialNumberSalesOutFragment serialNumberSalesOutFragment =  new SerialNumberSalesOutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detailReportSalesOutMainContent, serialNumberSalesOutFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
