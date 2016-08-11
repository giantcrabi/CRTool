package com.kreators.crtoolv1.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.kreators.crtoolv1.Model.SalesOutReport;
import com.kreators.crtoolv1.Network.GetVolleyRequest;
import com.kreators.crtoolv1.Network.VolleyListener;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.Network.VolleyRequest;
import com.kreators.crtoolv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    private Set<String> crOutletSalesOutList = new LinkedHashSet<>();
    private Set<String> crDateSalesOutList = new LinkedHashSet<>();

    protected FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales_out);
        retrieve();
        bind();
        getSalesOutData();
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
        Bundle dateList= new Bundle();
        dateList.putParcelableArray(Protocol.SN_DATE,crDateSalesOutList);
        adapter.addFragment(reportSalesOutByDateFragment, "By Date");

        ReportSalesOutByOutletFragment reportSalesOutByOutletFragment = new ReportSalesOutByOutletFragment();
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


    private void getSalesOutData() {
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
                    String a,b,c;
                    long d;
                    int e;
                    for(int i = 0; i < result.length(); i++) {
                        response = result.getJSONObject(i);

                        salesOutReport = new SalesOutReport(response.getLong(Protocol.SN),response.getString(Protocol.SN_OUTLET_NAME),
                                response.getString(Protocol.SN_DATE), response.getString(Protocol.SN_ITEM_DESC),
                                response.getInt(Protocol.SN_STATUS));
                        salesOutReportList.add(salesOutReport);
                    }

                    if (pd != null) {
                        pd.dismiss();
                    }

                    getCROutletSalesOut();
                    getCRDateSalesOut();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyRequest request, String errorMessage) {
                Toast.makeText(ReportSalesOutActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                if (pd != null) {
                    pd.dismiss();
                }
            }
        });
        volleyManager.createRequest(request,Protocol.GET);
    }

    private void getCROutletSalesOut () {
        int num;
        for(num=0; num < salesOutReportList.size();num++) {
            crOutletSalesOutList.add(salesOutReportList.get(num).getOutletName());
        }
    }
    private void getCRDateSalesOut () {
        int num;
        for(num=0; num < salesOutReportList.size();num++) {
            crDateSalesOutList.add(salesOutReportList.get(num).getPostDate());
        }
    }

}
