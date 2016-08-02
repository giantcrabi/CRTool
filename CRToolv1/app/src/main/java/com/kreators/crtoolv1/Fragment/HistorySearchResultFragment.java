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

public class HistorySearchResultFragment extends Fragment implements SearchView.OnQueryTextListener {
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<SerialNumber> snArrayList;
    private SNAdapter snAdapter;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_history_search_result, container, false);

        mSearchView=(SearchView) v.findViewById(R.id.svSearchOutletHistory);
        mListView=(ListView) v.findViewById(R.id.lvListViewOutletHistory);

        snArrayList= new ArrayList<>();
        snArrayList.add(new SerialNumber("Outlet Marina", "Sales Out: 5"));
        snArrayList.add(new SerialNumber("Outlet Datokromo Trade Centre", "Sales Out: 3"));
        snArrayList.add(new SerialNumber("Outlet Galaxy Mall", "Sales Out: 6"));
        snArrayList.add(new SerialNumber("Outlet THR", "Sales Out: 21"));
        snArrayList.add(new SerialNumber("Outlet Tunjungan Plaza", "Sales Out: 10"));

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
