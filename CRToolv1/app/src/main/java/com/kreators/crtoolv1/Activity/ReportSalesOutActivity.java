package com.kreators.crtoolv1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.Adapter.ViewPagerAdapter;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByDateFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByOutletFragment;
import com.kreators.crtoolv1.R;

/**
 * Created by Julio Anthony Leonar on 7/29/2016.
 */

public class ReportSalesOutActivity extends AppCompatActivity implements ReportSalesOutByDateFragment.ReportSalesOutByDateListener, ReportSalesOutByOutletFragment.ReportSalesOutByOutletListener {

    private Toolbar toolbar;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales_out);
        bind();
    }

    private void bind() {
        fragmentManager = getSupportFragmentManager();
        viewPager = (ViewPager) findViewById(R.id.activityDetailHotel);
        setupViewPager(viewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbarDetailHotel);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(fragmentManager);
        ReportSalesOutByDateFragment reportSalesOutByDateFragment =  new ReportSalesOutByDateFragment();
        ReportSalesOutByOutletFragment reportSalesOutByOutletFragment = new ReportSalesOutByOutletFragment();
        adapter.addFragment(reportSalesOutByDateFragment, "By Date");
        adapter.addFragment(reportSalesOutByOutletFragment, "By Outlet");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void adapterSalesOutByDateButtonClick() {
        Intent intent = new Intent(this, DetailReportSalesOutActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(Protocol.FRAGMENT_TAG,Constant.inflateFragmentByOutlet);
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public void adapterSalesOutByOutletButtonClick() {
        Intent intent = new Intent(this, DetailReportSalesOutActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(Protocol.FRAGMENT_TAG, Constant.inflateFragmentByDate);
        intent.putExtras(args);
        startActivity(intent);
    }
}
