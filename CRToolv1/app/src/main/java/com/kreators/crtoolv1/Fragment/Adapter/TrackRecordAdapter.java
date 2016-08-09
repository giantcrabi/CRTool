package com.kreators.crtoolv1.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kreators.crtoolv1.Model.TrackRecord;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;

/**
 * Created by Julio Anthony Leonar on 7/18/2016.
 */
public class TrackRecordAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<TrackRecord> trArrayList;

    public TrackRecordAdapter(Context context, ArrayList<TrackRecord> trackRecordArrayList) {
        super();
        this.context = context;
        this.trArrayList = trackRecordArrayList;
    }


    public class EmployeeHolder
    {
        TextView bulan;
        TextView appr;
        TextView retur;
        TextView presentasi;
    }



    @Override
    public int getCount() {
        return trArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return trArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EmployeeHolder holder;
        if(convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_track_record, parent, false);
            holder=new EmployeeHolder();
            holder.bulan=(TextView) convertView.findViewById(R.id.tvBulanTrackRecord);
            holder.appr=(TextView) convertView.findViewById(R.id.tvApprTrackRecord);
            holder.retur=(TextView) convertView.findViewById(R.id.tvReturTrackRecord);
            holder.presentasi=(TextView) convertView.findViewById(R.id.tvPresentasiTrackRecord);
            convertView.setTag(holder);
        }
        else {
            holder=(EmployeeHolder) convertView.getTag();
        }
        holder.bulan.setText(trArrayList.get(position).getBulan());
        holder.appr.setText(trArrayList.get(position).getAppr());
        holder.retur.setText(trArrayList.get(position).getRetur());
        holder.presentasi.setText(trArrayList.get(position).getPresentasi());
        return convertView;

    }

}