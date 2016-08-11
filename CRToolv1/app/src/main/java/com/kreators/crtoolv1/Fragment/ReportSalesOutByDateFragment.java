package com.kreators.crtoolv1.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.Adapter.SNAdapter;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class ReportSalesOutByDateFragment extends Fragment implements SearchView.OnQueryTextListener {
    private ReportSalesOutByDateListener activityCallBack;
    private SearchView mSearchView;
    private ListView mListView;
    private SNAdapter snAdapter;
    private List<String> crDateSalesOutList = new ArrayList<>();
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_report_sales_out, container, false);
        crDateSalesOutList = getArguments().getStringArrayList(Protocol.SN_DATE);
        bind();
        setupSearchView();
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterSalesOutByDateButtonClick();
            }
        });
    }

    private void bind() {
        mSearchView=(SearchView) v.findViewById(R.id.svSearchSN);
        mListView=(ListView) v.findViewById(R.id.lvListViewSN);
        snAdapter=new SNAdapter(getActivity(), (ArrayList<String>) crDateSalesOutList);
        mListView.setAdapter(snAdapter);
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
        if (context instanceof ReportSalesOutByDateListener) {
            activityCallBack = (ReportSalesOutByDateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReportMainListener");
        }
    }
    public void adapterSalesOutByDateButtonClick() {
        activityCallBack.adapterSalesOutByDateButtonClick();
    }
    public interface ReportSalesOutByDateListener {
        void adapterSalesOutByDateButtonClick();
    }


}
