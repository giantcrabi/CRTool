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
import com.kreators.crtoolv1.Network.GetVolleyRequest;
import com.kreators.crtoolv1.Network.VolleyListener;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.Network.VolleyRequest;
import com.kreators.crtoolv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
                    JSONObject response;

                    int crID=0;
                    boolean status=false;
                    String message="";
                    for(int i = 0; i < result.length(); i++) {
                        response = result.getJSONObject(i);
                        message = response.getString("message");
                        status = response.getBoolean("status");
                        if(response.has("ID")) crID = response.getInt("ID");
                    }
                    if (pd != null) {
                        pd.dismiss();
                    }
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

}
