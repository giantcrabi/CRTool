package com.kreators.crtoolv1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.kreators.crtoolv1.Fragment.Adapter.HistorySalesOutAdapter;
import com.kreators.crtoolv1.Model.SerialNumber;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;



public class SerialNumberSalesOutFragment extends Fragment implements SearchView.OnQueryTextListener {
    private View v;
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<SerialNumber> snArrayList;
    private HistorySalesOutAdapter snAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_serial_number_sales_out, container, false);
        bind();
        setupSearchView();
        return v;
    }

    private void bind() {
        mSearchView=(SearchView) v.findViewById(R.id.svSearchSerialNumberSalesOut);
        mListView=(ListView) v.findViewById(R.id.lvListViewSerialNumberSalesOut);
        snArrayList= new ArrayList<>();
        snArrayList.add(new SerialNumber("A1231231", "Submitted"));
        snArrayList.add(new SerialNumber("A1231232", "Submitted"));
        snArrayList.add(new SerialNumber("A1231233", "Appr"));
        snArrayList.add(new SerialNumber("A1231234", "Retur"));
        snAdapter=new HistorySalesOutAdapter(getActivity(), snArrayList);
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

}
