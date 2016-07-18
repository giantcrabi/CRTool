package com.kreators.crtoolv1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.kreators.crtoolv1.Fragment.Adapter.SNAdapter;
import com.kreators.crtoolv1.Model.SerialNumber;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class ReportSalesOutFragment extends Fragment implements SearchView.OnQueryTextListener {
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<SerialNumber> snArrayList;
    private SNAdapter snAdapter;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_report_sales_out, container, false);

        mSearchView=(SearchView) v.findViewById(R.id.svSearchSN);
        mListView=(ListView) v.findViewById(R.id.lvListViewSN);

        snArrayList= new ArrayList<>();
        snArrayList.add(new SerialNumber("21 Agustus 2016", "ABC93828983"));
        snArrayList.add(new SerialNumber("17 Agustus 2016", "ACB73844012"));
        snArrayList.add(new SerialNumber("6  Agustus 2016", "BVF10923881"));
        snArrayList.add(new SerialNumber("16 July    2016", "BRT92382175"));
        snArrayList.add(new SerialNumber("27 Juni    2016", "ANM20132412"));

        snAdapter=new SNAdapter(getActivity(), snArrayList);
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
