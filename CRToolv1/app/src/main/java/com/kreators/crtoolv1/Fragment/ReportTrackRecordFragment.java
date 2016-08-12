package com.kreators.crtoolv1.Fragment;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<TrackRecord> trackRecordList = new ArrayList<TrackRecord>();
    private SessionManager session;
    private VolleyManager volleyManager;
    private ProgressDialog pd;
    private String currentDate;
    private LineChart lineChart;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_report_track_record, container, false);
        initialization();
        fetchData();


        bind();


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(50f, 0));
        entries.add(new Entry(75f, 1));
        entries.add(new Entry(100f, 2));
        entries.add(new Entry(60f, 3));
        entries.add(new Entry(120f, 4));
        entries.add(new Entry(30f, 5));
        LineDataSet dataset = new LineDataSet(entries, "Pencapaian CR Selama 6 Bulan");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("FEB");
        labels.add("MAR");
        labels.add("APR");
        labels.add("MAY");
        labels.add("JUN");
        labels.add("JUL");
        LineData data = new LineData(labels, dataset);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        lineChart.setData(data);
        lineChart.animateY(3000);
////        mListView=(ListView) v.findViewById(R.id.lvListViewTrackRecord);
////        trAdapter=new TrackRecordAdapter(getActivity(), trArrayList);
//        mListView.setAdapter(trAdapter);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showTrackRecordDetails((TrackRecord) parent.getItemAtPosition(position));
//            }
//        });
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

    private void initialization() {
        volleyManager = VolleyManager.getInstance(getActivity().getApplicationContext());
        session = new SessionManager(getActivity().getApplicationContext());
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);

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

        int num;
        for(num=0;num<trackRecordList.size();num++) {
            date = sdf.parse(trackRecordList.get(num).getBulan());
            calendar.setTime(date);
            trackRecordList.get(num).setBulan(IndoCalendarFormat.getMonthName(calendar.getTimeInMillis()));
        }

        for(num=0;num<trackRecordList.size();num++) {
            date = sdf.parse(trackRecordList.get(num).getBulan());
            calendar.setTime(date);
            trackRecordList.get(num).setBulan(IndoCalendarFormat.getMonthName(calendar.getTimeInMillis()));
        }
    }

    private void bind() {
        lineChart = (LineChart) v.findViewById(R.id.chart);
        lineChart.setDescription("% Pencapaian");

    }


}