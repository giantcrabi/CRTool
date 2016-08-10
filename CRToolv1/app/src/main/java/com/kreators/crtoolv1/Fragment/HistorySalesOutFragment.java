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

import com.kreators.crtoolv1.Fragment.Adapter.HistorySalesOutAdapter;
import com.kreators.crtoolv1.Model.SerialNumber;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;

public class HistorySalesOutFragment extends Fragment implements SearchView.OnQueryTextListener {
    private View v;
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<SerialNumber> snArrayList;
    private HistorySalesOutAdapter snAdapter;
    private HistorySalesOutListener activityCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_history_sales_out, container, false);
        bind();
        setupSearchView();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterHistorySalesOutButtonClick();
            }
        });
    }


    private void bind() {
        mSearchView=(SearchView) v.findViewById(R.id.svSearchHistorySalesOut);
        mListView=(ListView) v.findViewById(R.id.lvListViewHistorySalesOut);
        snArrayList= new ArrayList<>();
        snArrayList.add(new SerialNumber("Laptop Model AXI-0 Axioo", "10"));
        snArrayList.add(new SerialNumber("HP Axioo Model AXI-0 ", "15"));
        snArrayList.add(new SerialNumber("Laptop Model AXI-1 Axioo", "5"));
        snArrayList.add(new SerialNumber("HP Model AXI-1 Axioo", "7"));

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HistorySalesOutListener) {
            activityCallBack = (HistorySalesOutListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCallBack=null;
    }

    public void adapterHistorySalesOutButtonClick() {
        activityCallBack.adapterHistorySalesOutButtonClick();
    }

    public interface HistorySalesOutListener {
        void adapterHistorySalesOutButtonClick();
    }
}
