package com.kreators.crtoolv1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.kreators.crtoolv1.Fragment.Adapter.ReportHistoryAdapter;
import com.kreators.crtoolv1.Model.SerialNumber;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class ReportHistoryFragment extends Fragment implements SearchView.OnQueryTextListener {
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<SerialNumber> snArrayList;
    private ReportHistoryAdapter snAdapter;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_report_history, container, false);

        mSearchView=(SearchView) v.findViewById(R.id.svSearchOutletHistory);
        mListView=(ListView) v.findViewById(R.id.lvListViewOutletHistory);

        snArrayList= new ArrayList<>();
        snArrayList.add(new SerialNumber("Outlet Marina", "Submit: 10, Approve: 10, Retur 1"));
        snArrayList.add(new SerialNumber("Outlet Datokromo Trade Centre", "Submit: 3, Approve: 13, Retur 0"));
        snArrayList.add(new SerialNumber("Outlet Galaxy Mall", "Submit: 6, Approve: 7, Retur 0"));
        snArrayList.add(new SerialNumber("Outlet THR", "Submit: 21, Approve: 15, Retur 0"));
        snArrayList.add(new SerialNumber("Outlet Tunjungan Plaza", "Submit: 23, Approve: 10, Retur 3"));

        snAdapter=new ReportHistoryAdapter(getActivity(), snArrayList);
        mListView.setAdapter(snAdapter);

        mListView.setTextFilterEnabled(true);
        setupSearchView();
        return v;
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

}
