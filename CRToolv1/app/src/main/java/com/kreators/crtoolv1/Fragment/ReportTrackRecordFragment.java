package com.kreators.crtoolv1.Fragment;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Commons.SessionManager;
import com.kreators.crtoolv1.Commons.Url;
import com.kreators.crtoolv1.Fragment.Adapter.TrackRecordAdapter;
import com.kreators.crtoolv1.Fragment.Dialog.TrackRecordDialogFragment;
import com.kreators.crtoolv1.Model.IndoCalendarFormat;
import com.kreators.crtoolv1.Model.TrackRecord;
import com.kreators.crtoolv1.Network.GetVolleyRequest;
import com.kreators.crtoolv1.Network.VolleyListener;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.Network.VolleyRequest;
import com.kreators.crtoolv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportTrackRecordFragment extends Fragment {

    private View v;
    private static final SimpleDateFormat sdf = new SimpleDateFormat(Constant.SYSTEM_DATE_STANDART, Locale.US);
    private ListView mListView;
    private ArrayList<TrackRecord> trArrayList;
    private TrackRecordAdapter trAdapter;
    private List<TrackRecord> trackRecordList = new ArrayList<>();
    private SessionManager session;
    private VolleyManager volleyManager;
    private ProgressDialog pd;
    private String currentDate;
    private LineChart lineChart;
    private int monthNow;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_report_track_record, container, false);
        initialization();
        fetchData();
        return v;
    }

    private void initialization() {
        getActivity().setTitle(Constant.fragmentTitleAchievement);
        volleyManager = VolleyManager.getInstance(getActivity().getApplicationContext());
        session = new SessionManager(getActivity().getApplicationContext());
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        mListView = (ListView) v.findViewById(R.id.lvListViewTrackRecord);
        Date dt = new Date();
        currentDate = sdf.format(dt);
    }

    private void fetchData(){
        pd.setTitle(Constant.salesOutDialog);
        pd.show();
        GetVolleyRequest request = new GetVolleyRequest(Url.TRACK_RECORD_REPORT);
        request.putParams(Protocol.USERID,session.getUserDetails().get(Protocol.USERID));
        request.putParams(Protocol.CUR_DATE,currentDate);
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                try {
                    JSONObject response;
                    TrackRecord trackRecord;

                    for(int i = 0; i < result.length(); i++) {
                        response = result.getJSONObject(i);
                        trackRecord = new TrackRecord(response.getLong(Protocol.PRICE),response.getString(Protocol.SN_DATE),response.getInt(Protocol.SN_STATUS));
                        trackRecordList.add(trackRecord);
                    }
                    if (pd != null) {
                        pd.dismiss();
                    }
                    try {
                        setUpData();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyRequest request, String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                if (pd != null) {
                    pd.dismiss();
                }
            }
        });
        volleyManager.createRequest(request,Protocol.GET);
    }

    private void setUpData() throws ParseException {

        Calendar calendar = Calendar.getInstance();
        Date date;
        date = sdf.parse(currentDate);
        calendar.setTime(date);
        monthNow = IndoCalendarFormat.getMonthIndex(calendar.getTimeInMillis());

        int num;
        for(num=0;num<trackRecordList.size();num++) {
            date = sdf.parse(trackRecordList.get(num).getBulan());
            calendar.setTime(date);
            trackRecordList.get(num).setBulan(IndoCalendarFormat.getMonthNameShort(calendar.getTimeInMillis()));
        }

        TrackRecord trackRecordListDummy[] = new TrackRecord[6];
        int monthNowDummy = monthNow;
        for(num=5;num>=0;num--) {
            String bulanNow = IndoCalendarFormat.getMonthSimpleNameFromIndex(monthNowDummy);
            monthNowDummy--;
            if(monthNowDummy == 0 ) monthNowDummy = 12;
            trackRecordListDummy[num] = new TrackRecord(0,bulanNow,0,0,0,0);
        }


        for (TrackRecord i : trackRecordList) {
            int bulanIndex = IndoCalendarFormat.getMonthIndexFromSimpleName(i.getBulan());
            num = bulanIndex - monthNow + 5;
            trackRecordListDummy[num].setPrice(trackRecordListDummy[num].getPrice()+i.getPrice());
            int status = i.getStatus();
            switch(status){
                case 0:
                    trackRecordListDummy[num].setSubmitted(trackRecordListDummy[num].getSubmitted()+1);
                    break;
                case 1:
                    trackRecordListDummy[num].setReceived(trackRecordListDummy[num].getReceived()+1);
                    break;
                case 2:
                    trackRecordListDummy[num].setApproved(trackRecordListDummy[num].getApproved()+1);
                    break;
                case 3:
                    trackRecordListDummy[num].setRetur(trackRecordListDummy[num].getRetur()+1);
                    break;
                default:
                    break;
            }
        }
        trackRecordList.clear();

        for(int i=0;i<6;i++){
            trackRecordList.add(trackRecordListDummy[i]);
        }
        monthNow = monthNowDummy;

        bind();
    }

    private void bind() {
        lineChart = (LineChart) v.findViewById(R.id.chart);
        lineChart.setDescription("% Pencapaian");
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        for(int i=0;i<trackRecordList.size();i++) {
            labels.add(trackRecordList.get(i).getBulan());
            entries.add(new Entry((float)trackRecordList.get(i).getPrice()/3000000,i));
        }
        LineDataSet dataset = new LineDataSet(entries, "CR Achievement Last 6 Month");
        LineData data = new LineData(labels, dataset);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        lineChart.setData(data);
        lineChart.animateY(3000);
        trAdapter=new TrackRecordAdapter(getContext(), (ArrayList<TrackRecord>) trackRecordList);
        mListView.setAdapter(trAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTrackRecordDetails((TrackRecord) parent.getItemAtPosition(position));
            }
        });
    }

    private void showTrackRecordDetails(TrackRecord trackRecordDetails) {
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        android.app.Fragment prev = getActivity().getFragmentManager().findFragmentByTag("Report Track Record");
        if (prev != null) {
            ft.remove(prev);
        }
        TrackRecordDialogFragment TR = TrackRecordDialogFragment.newInstance(trackRecordDetails);
        TR.show(ft,"Report Track Record");
    }
}