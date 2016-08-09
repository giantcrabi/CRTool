package com.kreators.crtoolv1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.kreators.crtoolv1.Fragment.Adapter.SNAdapter;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class ReportSalesOutByOutletFragment extends Fragment implements SearchView.OnQueryTextListener {
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<String> snArrayList;
    private SNAdapter snAdapter;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_report_sales_out, container, false);

        mSearchView=(SearchView) v.findViewById(R.id.svSearchSN);
        mListView=(ListView) v.findViewById(R.id.lvListViewSN);

        snArrayList= new ArrayList<>();
        snArrayList.add("Outlet Marina");
        snArrayList.add("Outlet Datokromo Trade Centre");
        snArrayList.add("Outlet Galaxy Mall");
        snArrayList.add("Outlet THR");
        snArrayList.add("Outlet Tunjungan Plaza");

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //onClick
            }
        });
    }
}
