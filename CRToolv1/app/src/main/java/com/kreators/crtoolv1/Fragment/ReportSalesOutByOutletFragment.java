package com.kreators.crtoolv1.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.Adapter.HistorySalesOutAdapter;
import com.kreators.crtoolv1.Model.Report;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class ReportSalesOutByOutletFragment extends Fragment implements SearchView.OnQueryTextListener {
    private ReportSalesOutByOutletListener activityCallBack;
    private SearchView mSearchView;
    private ListView mListView;
    private HistorySalesOutAdapter reportAdapter;
    private List<Report> crOutletSalesOutList = new ArrayList<>();
    private InputMethodManager imm;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_report_sales_out, container, false);
        retrieve();
        bind();
        setupSearchView();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterSalesOutByOutletButtonClick(crOutletSalesOutList.get(position));
            }
        });
    }

    private void retrieve() {
        getActivity().setTitle(Constant.fragmentTitleSalesOut + Constant.fragmentTitleByOutlet);
        crOutletSalesOutList = (List<Report>) getArguments().getSerializable(Protocol.SN_OUTLET_NAME);
    }

    private void bind() {
        mSearchView=(SearchView) v.findViewById(R.id.svSearchSN);
        mListView=(ListView) v.findViewById(R.id.lvListViewSN);
        reportAdapter =new HistorySalesOutAdapter(getActivity(), (ArrayList<Report>) crOutletSalesOutList);
        mListView.setAdapter(reportAdapter);
        mListView.setTextFilterEnabled(true);
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReportSalesOutByOutletListener) {
            activityCallBack = (ReportSalesOutByOutletListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReportMainListener");
        }
    }
    public void adapterSalesOutByOutletButtonClick(Report outletClicked) {
        activityCallBack.adapterSalesOutByOutletButtonClick(outletClicked);
    }
    public interface ReportSalesOutByOutletListener {
        void adapterSalesOutByOutletButtonClick(Report outletClicked);
    }




}
