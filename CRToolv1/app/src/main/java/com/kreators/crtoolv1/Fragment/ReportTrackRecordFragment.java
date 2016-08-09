package com.kreators.crtoolv1.Fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.kreators.crtoolv1.Fragment.Adapter.TrackRecordAdapter;
import com.kreators.crtoolv1.Fragment.Dialog.TrackRecordDialogFragment;
import com.kreators.crtoolv1.Model.TrackRecord;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;

public class ReportTrackRecordFragment extends Fragment {

    private View v;
    private ListView mListView;
    private ArrayList<TrackRecord> trArrayList;
    private TrackRecordAdapter trAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_report_track_record, container, false);

        LineChart lineChart = (LineChart) v.findViewById(R.id.chart);
        lineChart.setDescription("% Pencapaian");

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
        mListView=(ListView) v.findViewById(R.id.lvListViewTrackRecord);
        trArrayList= new ArrayList<>();
        trArrayList.add(new TrackRecord("February","Rp. 225.000","Rp 300.000","75%"));
        trArrayList.add(new TrackRecord("Maret","Rp. 300.000","Rp 300.000","100%"));
        trArrayList.add(new TrackRecord("April", "Rp. 180.000","Rp 300.000","60%"));
        trArrayList.add(new TrackRecord("Mei", "Rp. 360.000","Rp 300.000","120%"));
        trArrayList.add(new TrackRecord("Juni", "Rp. 100.000","Rp 300.000","33%"));
        trArrayList.add(new TrackRecord("Juli", "Rp. 200.000","Rp 300.000","67%"));
        trAdapter=new TrackRecordAdapter(getActivity(), trArrayList);
        mListView.setAdapter(trAdapter);
        return v;
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