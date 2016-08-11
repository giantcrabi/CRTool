package com.kreators.crtoolv1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SerialNumberSalesOutFragment extends Fragment implements SearchView.OnQueryTextListener {
    private View v;
    private SearchView mSearchView;
    private static final SimpleDateFormat dateStandartFormatter = new SimpleDateFormat(Constant.SYSTEM_DATE_STANDART, Locale.US);
    private ListView mListView;
    private ArrayList<SerialNumber> crSerialNumber = new ArrayList<>();
    private HistorySalesOutAdapter snAdapter;
    private String dateSelected, outletSelected, itemSelected;
    private List<SalesOutReport> salesOutReportList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_serial_number_sales_out, container, false);
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
        itemSelected = (String) getArguments().getSerializable(Protocol.SN_ITEM_DESC);
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
                   if(salesOutReportList.get(num).getItemDesc().equals(itemSelected)) {
                       int status;
                       status = salesOutReportList.get(num).getInctvStatus();
                       String statusInString = "";
                       switch(status){
                           case 0:
                               statusInString = "Submitted";
                               break;
                           case 1:
                               statusInString = "Received";
                               break;
                           case 2:
                               statusInString = "Approved";
                               break;
                           case 3:
                               statusInString = "Retur";
                               break;
                           default:
                               break;
                       }
                       crSerialNumber.add(new SerialNumber(String.valueOf(salesOutReportList.get(num).getSN()),statusInString));
                   }
                }
            }
        }
    }

    private void bind() {
        mSearchView=(SearchView) v.findViewById(R.id.svSearchSerialNumberSalesOut);
        mListView=(ListView) v.findViewById(R.id.lvListViewSerialNumberSalesOut);
        snAdapter=new HistorySalesOutAdapter(getActivity(), crSerialNumber);
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
