package com.kreators.crtoolv1.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Commons.SessionManager;
import com.kreators.crtoolv1.Commons.Url;
import com.kreators.crtoolv1.Fragment.Adapter.ViewPagerAdapter;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByDateFragment;
import com.kreators.crtoolv1.Fragment.ReportSalesOutByOutletFragment;
import com.kreators.crtoolv1.Model.DateParameter;
import com.kreators.crtoolv1.Model.IndoCalendarFormat;
import com.kreators.crtoolv1.Model.SalesOutReport;
import com.kreators.crtoolv1.Network.GetVolleyRequest;
import com.kreators.crtoolv1.Network.VolleyListener;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.Network.VolleyRequest;
import com.kreators.crtoolv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by Julio Anthony Leonar on 7/29/2016.
 */

public class ReportSalesOutActivity extends AppCompatActivity implements ReportSalesOutByDateFragment.ReportSalesOutByDateListener, ReportSalesOutByOutletFragment.ReportSalesOutByOutletListener {
    private ProgressDialog pd;
    private SessionManager session;
    private VolleyManager volleyManager;
    private Toolbar toolbar;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DateParameter dateParameter;
    private HashMap<String, String> userLogin = new HashMap<>();
    private List<SalesOutReport> salesOutReportList = new ArrayList<>();
    private ArrayList<String> crOutletSalesOutList = new ArrayList<>();
    private ArrayList<String> crDateSalesOutList = new ArrayList<>();
    private static final SimpleDateFormat dateStandartFormatter = new SimpleDateFormat(Constant.SYSTEM_DATE_STANDART, Locale.US);
    protected FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales_out);
        retrieve();
        setUpData();
    }

    private void retrieve(){
        dateParameter = getIntent().getExtras().getParcelable(Protocol.DATE_PARAMETER);
        volleyManager = VolleyManager.getInstance(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        userLogin = session.getUserDetails();
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
    }
    public void bind() {
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
        Bundle argsDate = new Bundle();
        argsDate.putStringArrayList(Protocol.SN_DATE,crDateSalesOutList);
        reportSalesOutByDateFragment.setArguments(argsDate);
        adapter.addFragment(reportSalesOutByDateFragment, Constant.fragmentTitleByDate);


        ReportSalesOutByOutletFragment reportSalesOutByOutletFragment = new ReportSalesOutByOutletFragment();
        Bundle argsOutlet = new Bundle();
        argsOutlet.putStringArrayList(Protocol.SN_OUTLET_NAME,crOutletSalesOutList);
        reportSalesOutByOutletFragment.setArguments(argsOutlet);
        adapter.addFragment(reportSalesOutByOutletFragment,Constant.fragmentTitleByOutlet);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void adapterSalesOutByDateButtonClick(String dateClicked) {
        Intent intent = new Intent(this, DetailReportSalesOutActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(Protocol.FRAGMENT_TAG,Constant.inflateFragmentByOutlet);
        args.putSerializable(Protocol.SN_DATE, dateClicked);
        args.putParcelableArrayList(Protocol.SO_REPORT,(ArrayList<? extends Parcelable>) salesOutReportList);
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public void adapterSalesOutByOutletButtonClick(String outletClicked) {
        Intent intent = new Intent(this, DetailReportSalesOutActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(Protocol.FRAGMENT_TAG, Constant.inflateFragmentByDate);
        args.putSerializable(Protocol.SN_OUTLET_NAME, outletClicked);
        args.putParcelableArrayList(Protocol.SO_REPORT,(ArrayList<? extends Parcelable>) salesOutReportList);
        intent.putExtras(args);
        startActivity(intent);
    }


    private void setUpData() {
        pd.setTitle(Constant.salesOutDialog);
        pd.show();
        GetVolleyRequest request = new GetVolleyRequest(Url.GET_SALES_OUT_REPORT);
        request.putParams(Protocol.USERID,userLogin.get(Protocol.USERID));
        request.putParams(Protocol.DATE_FROM,dateParameter.getDateFrom());
        request.putParams(Protocol.DATE_TO,dateParameter.getDateTo());
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                try {
                    SalesOutReport salesOutReport;
                    JSONObject response;
                    boolean status = true;
                    String message = "";

                    for(int i = 0; i < result.length(); i++) {
                        response = result.getJSONObject(i);

                        if(response.has(Protocol.STATUS)) {
                            status = response.getBoolean(Protocol.STATUS);
                            message = response.getString(Protocol.MESSAGE);
                            break;
                        }

                        salesOutReport = new SalesOutReport(response.getLong(Protocol.SN),response.getString(Protocol.SN_OUTLET_NAME),
                                response.getString(Protocol.SN_DATE), response.getString(Protocol.SN_ITEM_DESC),
                                response.getInt(Protocol.SN_STATUS));
                        salesOutReportList.add(salesOutReport);
                    }

                    if (pd != null) {
                        pd.dismiss();
                    }

                    if (status) {
                        getCROutletSalesOut();
                        getCRDateSalesOut();
                        bind();
                    } else {
                        ReportSalesOutActivity.this.finish();
                        Toast.makeText(ReportSalesOutActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyRequest request, String errorMessage) {
                finish();
                Toast.makeText(ReportSalesOutActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                if (pd != null) {
                    pd.dismiss();
                }
            }
        });
        volleyManager.createRequest(request,Protocol.GET);
    }

    private void getCROutletSalesOut (){
        int num;

        for(num=0; num < salesOutReportList.size();num++) {
            crOutletSalesOutList.add(salesOutReportList.get(num).getOutletName());
        }
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(crOutletSalesOutList);
        crOutletSalesOutList.clear();
        crOutletSalesOutList.addAll(hashSet);
        hashSet.clear();
    }
    private void getCRDateSalesOut () throws ParseException {
        int num;
        Calendar calendar = Calendar.getInstance();
        Date date;
        for(num=0; num < salesOutReportList.size();num++) {
            date = dateStandartFormatter.parse(salesOutReportList.get(num).getPostDate());
            calendar.setTime(date);
            crDateSalesOutList.add(IndoCalendarFormat.getDate(calendar.getTimeInMillis()));
        }
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(crDateSalesOutList);
        crDateSalesOutList.clear();
        crDateSalesOutList.addAll(hashSet);
        hashSet.clear();
    }

}
