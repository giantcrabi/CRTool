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

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.Adapter.HistorySalesOutAdapter;
import com.kreators.crtoolv1.Model.IndoCalendarFormat;
import com.kreators.crtoolv1.Model.SalesOutReport;
import com.kreators.crtoolv1.Model.SerialNumber;
import com.kreators.crtoolv1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class HistorySalesOutFragment extends Fragment implements SearchView.OnQueryTextListener {
    private View v;
    private static final SimpleDateFormat dateStandartFormatter = new SimpleDateFormat(Constant.SYSTEM_DATE_STANDART, Locale.US);
    private SearchView mSearchView;
    private ListView mListView;
    private HistorySalesOutAdapter snAdapter;
    private HistorySalesOutListener activityCallBack;
    private String dateSelected, outletSelected;
    private List<SalesOutReport> salesOutReportList = new ArrayList<>();
    private List<SerialNumber> itemName = new ArrayList<>();
    private ArrayList<String> crItemNameList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_history_sales_out, container, false);
        retrieve();
        try {
            setUpData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bind();
        setupSearchView();
        return v;
    }

    private void retrieve() {
        dateSelected = (String) getArguments().getSerializable(Protocol.SN_DATE);
        outletSelected = (String) getArguments().getSerializable(Protocol.SN_OUTLET_NAME);
        salesOutReportList = getArguments().getParcelableArrayList(Protocol.SO_REPORT);
    }

    private void setUpData() throws ParseException {
        int num;
        Calendar calendar = Calendar.getInstance();
        Date date;
        for(num=0;num<salesOutReportList.size();num++) {
            if(salesOutReportList.get(num).getOutletName().equals(outletSelected)){
                date = dateStandartFormatter.parse(salesOutReportList.get(num).getPostDate());
                calendar.setTime(date);
                if (IndoCalendarFormat.getDate(calendar.getTimeInMillis()).equals(dateSelected)) {
                    crItemNameList.add(salesOutReportList.get(num).getItemDesc());
                }
            }
        }

        HashMap<String, String> hashSet = new HashMap<String, String>();
        for(num=0;num<crItemNameList.size();num++){
            hashSet.put(crItemNameList.get(num),String.valueOf(Collections.frequency(crItemNameList, crItemNameList.get(num))));
        }

        Iterator hashSetIterator = hashSet.keySet().iterator();
        while(hashSetIterator.hasNext()) {
            String key=(String)hashSetIterator.next();
            String value=(String)hashSet.get(key);
            itemName.add(new SerialNumber(key,value));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterHistorySalesOutButtonClick(dateSelected,outletSelected,itemName.get(position).getDate());
            }
        });
    }


    private void bind() {
        mSearchView=(SearchView) v.findViewById(R.id.svSearchHistorySalesOut);
        mListView=(ListView) v.findViewById(R.id.lvListViewHistorySalesOut);
        snAdapter=new HistorySalesOutAdapter(getActivity(), (ArrayList<SerialNumber>) itemName);
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

    public void adapterHistorySalesOutButtonClick(String date, String outlet, String name) {
        activityCallBack.adapterHistorySalesOutButtonClick(date,outlet,name);
    }

    public interface HistorySalesOutListener {
        void adapterHistorySalesOutButtonClick(String date, String outlet, String name);
    }
}
