package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.kreators.crtoolv1.Fragment.ReportSalesOutByDateFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByOutletFragment;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julio Anthony Leonar on 7/29/2016.
 */

public class ReportSalesOutActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales_out);

        fragmentManager = getSupportFragmentManager();
        viewPager = (ViewPager) findViewById(R.id.activityDetailHotel);
        setupViewPager(viewPager);

        toolbar = (Toolbar) findViewById(R.id.toolbarDetailHotel); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ReportSalesOutByDateFragment reportSalesOutByDateFragment =  new ReportSalesOutByDateFragment();
        ReportSalesOutByOutletFragment reportSalesOutByOutletFragment = new ReportSalesOutByOutletFragment();

        adapter.addFragment(reportSalesOutByDateFragment, "By Date");
        adapter.addFragment(reportSalesOutByOutletFragment, "By Outlet");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report_sales_out, menu);
        return true;
    }

    public void addFragment(Fragment fragment) {
        fragmentManager.beginTransaction().add(R.id.main_content, fragment).addToBackStack("add").commit();
    }


}
