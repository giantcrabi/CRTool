package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.ReportHistorySalesOutFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByDateFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByOutletFragment;
import com.kreators.crtoolv1.Fragment.ReportSerialNumberSalesOutFragment;
import com.kreators.crtoolv1.Model.IndoCalendarFormat;
import com.kreators.crtoolv1.Model.Report;
import com.kreators.crtoolv1.Model.SalesOutReport;
import com.kreators.crtoolv1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class DetailReportSalesOutActivity extends AppCompatActivity implements ReportSalesOutByOutletFragment.ReportSalesOutByOutletListener,ReportSalesOutByDateFragment.ReportSalesOutByDateListener,ReportHistorySalesOutFragment.HistorySalesOutListener {
    private static final SimpleDateFormat dateStandartFormatter = new SimpleDateFormat(Constant.SYSTEM_DATE_STANDART, Locale.US);
    private String fragmentToInflate;
    private String dateSelected, outletSelected;
    private List<SalesOutReport> salesOutReportList = new ArrayList<>();
    private ArrayList<Report> crOutletSalesOutList = new ArrayList<>();
    private ArrayList<Report> crDateSalesOutList = new ArrayList<>();

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
    public void adapterSalesOutByDateButtonClick(Report dateClicked) {
        ReportHistorySalesOutFragment reportHistorySalesOutFragment =  new ReportHistorySalesOutFragment();
        Bundle args = new Bundle();
        args.putSerializable(Protocol.SN_OUTLET_NAME, outletSelected);
        args.putSerializable(Protocol.SN_DATE, dateClicked.getDate());
        args.putParcelableArrayList(Protocol.SO_REPORT,(ArrayList<? extends Parcelable>) salesOutReportList);
        reportHistorySalesOutFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detailReportSalesOutMainContent, reportHistorySalesOutFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void adapterSalesOutByOutletButtonClick(Report outletClicked) {
        ReportHistorySalesOutFragment reportHistorySalesOutFragment =  new ReportHistorySalesOutFragment();
        Bundle args = new Bundle();
        args.putSerializable(Protocol.SN_OUTLET_NAME, outletClicked.getDate());
        args.putSerializable(Protocol.SN_DATE, dateSelected);
        args.putParcelableArrayList(Protocol.SO_REPORT,(ArrayList<? extends Parcelable>) salesOutReportList);
        reportHistorySalesOutFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detailReportSalesOutMainContent, reportHistorySalesOutFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void adapterHistorySalesOutButtonClick(String date, String outlet, String name) {
        ReportSerialNumberSalesOutFragment reportSerialNumberSalesOutFragment =  new ReportSerialNumberSalesOutFragment();
        Bundle args = new Bundle();
        args.putSerializable(Protocol.SN_OUTLET_NAME, outlet);
        args.putSerializable(Protocol.SN_DATE, date);
        args.putSerializable(Protocol.SN_ITEM_DESC, name);
        args.putParcelableArrayList(Protocol.SO_REPORT,(ArrayList<? extends Parcelable>) salesOutReportList);
        reportSerialNumberSalesOutFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detailReportSalesOutMainContent, reportSerialNumberSalesOutFragment);
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
            }
            salesOutReportList = args.getParcelableArrayList(Protocol.SO_REPORT);
        }

    }

    public void setUpData() throws ParseException {
        if(fragmentToInflate.equals(Constant.inflateFragmentByDate)) {
            int i,count;
            Calendar calendar = Calendar.getInstance();
            Date date;
            HashMap<String, String> hashSet = new HashMap<>();
            for(i=0; i < salesOutReportList.size();i++) {
                if(salesOutReportList.get(i).getOutletName().equals(outletSelected)) {
                    date = dateStandartFormatter.parse(salesOutReportList.get(i).getPostDate());
                    calendar.setTime(date);
                    if (hashSet.get(IndoCalendarFormat.getDate(calendar.getTimeInMillis())) == null) {
                        count = 1;
                    } else {
                        count = Integer.valueOf(hashSet.get(IndoCalendarFormat.getDate(calendar.getTimeInMillis()))) + 1;
                    }
                    hashSet.put(IndoCalendarFormat.getDate(calendar.getTimeInMillis()), String.valueOf(count));
                }
            }

            Iterator hashSetIterator = hashSet.keySet().iterator();
            while(hashSetIterator.hasNext()) {
                String key=(String)hashSetIterator.next();
                String value=hashSet.get(key);
                crDateSalesOutList.add(new Report(key,value));
            }
            hashSet.clear();
        } else if (fragmentToInflate.equals(Constant.inflateFragmentByOutlet)) {
            int i,count;
            HashMap<String, String> hashSet = new HashMap<>();
            Calendar calendar = Calendar.getInstance();
            Date date;
            for(i=0; i < salesOutReportList.size();i++) {
                date = dateStandartFormatter.parse(salesOutReportList.get(i).getPostDate());
                calendar.setTime(date);
                if((IndoCalendarFormat.getDate(calendar.getTimeInMillis())).equals(dateSelected)) {
                    if (hashSet.get(salesOutReportList.get(i).getOutletName()) == null) {
                        count = 1;
                    } else {
                        count = Integer.valueOf(hashSet.get(salesOutReportList.get(i).getOutletName()))+1 ;
                    }
                    hashSet.put(salesOutReportList.get(i).getOutletName(),String.valueOf(count));
                }
            }

            Iterator hashSetIterator = hashSet.keySet().iterator();
            while(hashSetIterator.hasNext()) {
                String key=(String)hashSetIterator.next();
                String value=hashSet.get(key);
                crOutletSalesOutList.add(new Report(key,value));
            }
            hashSet.clear();
        }

    }

    public void initialization() {
        if(fragmentToInflate.equals(Constant.inflateFragmentByDate)) {
            ReportSalesOutByDateFragment reportSalesOutByDateFragment = new ReportSalesOutByDateFragment();
            Bundle argsDate = new Bundle();
            argsDate.putSerializable(Protocol.SN_DATE,crDateSalesOutList);
            reportSalesOutByDateFragment.setArguments(argsDate);
            getSupportFragmentManager().beginTransaction().add(R.id.detailReportSalesOutMainContent, reportSalesOutByDateFragment).commit();
        } else if (fragmentToInflate.equals(Constant.inflateFragmentByOutlet)) {
            ReportSalesOutByOutletFragment reportSalesOutByOutletFragment = new ReportSalesOutByOutletFragment();
            Bundle argsOutlet = new Bundle();
            argsOutlet.putSerializable(Protocol.SN_OUTLET_NAME,crOutletSalesOutList);
            reportSalesOutByOutletFragment.setArguments(argsOutlet);
            getSupportFragmentManager().beginTransaction().add(R.id.detailReportSalesOutMainContent, reportSalesOutByOutletFragment).commit();
        }

    }
}
