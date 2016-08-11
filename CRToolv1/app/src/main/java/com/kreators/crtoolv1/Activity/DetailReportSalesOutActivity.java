package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.HistorySalesOutFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByDateFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByOutletFragment;
import com.kreators.crtoolv1.Fragment.SerialNumberSalesOutFragment;
import com.kreators.crtoolv1.Model.IndoCalendarFormat;
import com.kreators.crtoolv1.Model.SalesOutReport;
import com.kreators.crtoolv1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class DetailReportSalesOutActivity extends AppCompatActivity implements ReportSalesOutByOutletFragment.ReportSalesOutByOutletListener,ReportSalesOutByDateFragment.ReportSalesOutByDateListener,HistorySalesOutFragment.HistorySalesOutListener {
    private static final SimpleDateFormat dateStandartFormatter = new SimpleDateFormat(Constant.SYSTEM_DATE_STANDART, Locale.US);
    private String fragmentToInflate;
    private String dateSelected, outletSelected;
    private List<SalesOutReport> salesOutReportList = new ArrayList<>();
    private ArrayList<String> crOutletSalesOutList = new ArrayList<>();
    private ArrayList<String> crDateSalesOutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report_sales_out);
        if (findViewById(R.id.detailReportSalesOutMainContent) != null) {
            if (savedInstanceState != null) {
                return;
            }
            retrieve();
            try {
                setUpData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            initialization();
        }
    }

    @Override
    public void adapterSalesOutByDateButtonClick(String dateClicked) {
        HistorySalesOutFragment historySalesOutFragment=  new HistorySalesOutFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detailReportSalesOutMainContent, historySalesOutFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void adapterSalesOutByOutletButtonClick(String outletClicked) {
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

    public void retrieve() {
        Bundle args = getIntent().getExtras();
        if(args != null){
            fragmentToInflate = (String) args.getSerializable(Protocol.FRAGMENT_TAG);
            if(fragmentToInflate.equals(Constant.inflateFragmentByDate)) {
                outletSelected = (String) args.getSerializable(Protocol.SN_OUTLET_NAME);
            } else if (fragmentToInflate.equals(Constant.inflateFragmentByOutlet)) {
                dateSelected = (String) args.getSerializable(Protocol.SN_DATE);
                Log.d("a","a");
            }
            salesOutReportList = args.getParcelableArrayList(Protocol.SO_REPORT);
        }

    }

    public void setUpData() throws ParseException {
        if(fragmentToInflate.equals(Constant.inflateFragmentByDate)) {
            int num;
            Calendar calendar = Calendar.getInstance();
            Date date;

            for(num=0;num<salesOutReportList.size();num++) {
                if(salesOutReportList.get(num).getOutletName().equals(outletSelected)) {
                    date = dateStandartFormatter.parse(salesOutReportList.get(num).getPostDate());
                    calendar.setTime(date);
                    crDateSalesOutList.add(IndoCalendarFormat.getDate(calendar.getTimeInMillis()));
                }
            }
            HashSet<String> hashSet = new HashSet<>();
            hashSet.addAll(crDateSalesOutList);
            crDateSalesOutList.clear();
            crDateSalesOutList.addAll(hashSet);
            hashSet.clear();

        } else if (fragmentToInflate.equals(Constant.inflateFragmentByOutlet)) {
            int num;
            Calendar calendar = Calendar.getInstance();
            Date date;
            for(num=0;num<salesOutReportList.size();num++) {
                date = dateStandartFormatter.parse(salesOutReportList.get(num).getPostDate());
                calendar.setTime(date);
                if((IndoCalendarFormat.getDate(calendar.getTimeInMillis())).equals(dateSelected)) {
                    crOutletSalesOutList.add(salesOutReportList.get(num).getOutletName());
                }
            }
            HashSet<String> hashSet = new HashSet<>();
            hashSet.addAll(crOutletSalesOutList);
            crOutletSalesOutList.clear();
            crOutletSalesOutList.addAll(hashSet);
            hashSet.clear();
        }

    }

    public void initialization() {
        if(fragmentToInflate.equals(Constant.inflateFragmentByDate)) {
            ReportSalesOutByDateFragment reportSalesOutByDateFragment = new ReportSalesOutByDateFragment();
            Bundle argsDate = new Bundle();
            argsDate.putStringArrayList(Protocol.SN_DATE,crDateSalesOutList);
            reportSalesOutByDateFragment.setArguments(argsDate);
            getSupportFragmentManager().beginTransaction().add(R.id.detailReportSalesOutMainContent, reportSalesOutByDateFragment).commit();
        } else if (fragmentToInflate.equals(Constant.inflateFragmentByOutlet)) {
            ReportSalesOutByOutletFragment reportSalesOutByOutletFragment = new ReportSalesOutByOutletFragment();
            Bundle argsOutlet = new Bundle();
            argsOutlet.putStringArrayList(Protocol.SN_OUTLET_NAME,crOutletSalesOutList);
            reportSalesOutByOutletFragment.setArguments(argsOutlet);
            getSupportFragmentManager().beginTransaction().add(R.id.detailReportSalesOutMainContent, reportSalesOutByOutletFragment).commit();
        }

    }
}
