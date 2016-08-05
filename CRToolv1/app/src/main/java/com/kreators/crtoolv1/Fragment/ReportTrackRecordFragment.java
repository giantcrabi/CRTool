package com.kreators.crtoolv1.Fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.kreators.crtoolv1.Fragment.Adapter.TrackRecordAdapter;
import com.kreators.crtoolv1.Fragment.Dialog.TrackRecordDialogFragment;
import com.kreators.crtoolv1.Model.SerialNumber;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;

public class ReportTrackRecordFragment extends Fragment {

    private View v;
    private ListView mListView;
    private ArrayList<SerialNumber> trArrayList;
    private TrackRecordAdapter trAdapter;
    private TextView tableTitle;


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
        entries.add(new Entry(68f, 6));
        entries.add(new Entry(93f, 7));
        entries.add(new Entry(120f, 8));
        entries.add(new Entry(200f, 9));
        entries.add(new Entry(250f, 10));
        entries.add(new Entry(80f, 11));

        LineDataSet dataset = new LineDataSet(entries, "Pencapaian CR Perbulan");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("JAN");
        labels.add("FEB");
        labels.add("MAR");
        labels.add("APR");
        labels.add("MAY");
        labels.add("JUN");
        labels.add("JUL");
        labels.add("AUG");
        labels.add("SEP");
        labels.add("OKT");
        labels.add("NOV");
        labels.add("DEC");

        LineData data = new LineData(labels, dataset);

        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(4000);

        tableTitle = (TextView) v.findViewById(R.id.tvTableHistoryTitle);
        tableTitle.setText(tableTitle.getText() + " 2016");


        mListView=(ListView) v.findViewById(R.id.lvListViewTrackRecord);
        trArrayList= new ArrayList<>();
        trArrayList.add(new SerialNumber("January", "50%"));
        trArrayList.add(new SerialNumber("February", "75%"));
        trArrayList.add(new SerialNumber("Maret", "100%"));
        trArrayList.add(new SerialNumber("April", "60%"));
        trArrayList.add(new SerialNumber("Mei", "120%"));
        trArrayList.add(new SerialNumber("Juni", "30%"));
        trArrayList.add(new SerialNumber("Juli", "68%"));
        trArrayList.add(new SerialNumber("Agustus", "93%"));
        trArrayList.add(new SerialNumber("September", "120%"));
        trArrayList.add(new SerialNumber("Oktober", "200%"));
        trArrayList.add(new SerialNumber("November", "250%"));
        trArrayList.add(new SerialNumber("Desember", "80%"));
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
                showTrackRecordDetails((SerialNumber) parent.getItemAtPosition(position));
            }
        });
    }

    private void showTrackRecordDetails(SerialNumber trackRecordDetails) {
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        android.app.Fragment prev = getActivity().getFragmentManager().findFragmentByTag("Report Track Record");
        if (prev != null) {
            ft.remove(prev);
        }
        TrackRecordDialogFragment TR = TrackRecordDialogFragment.newInstance(trackRecordDetails);
        TR.show(ft,"Report Track Record");
    }

}

