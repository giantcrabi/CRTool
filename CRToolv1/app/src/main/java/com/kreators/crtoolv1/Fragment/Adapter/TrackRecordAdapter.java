package com.kreators.crtoolv1.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kreators.crtoolv1.Model.IndoCurrencyFormat;
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




    public class TrackRecordHolder
    {
        TextView bulan;
        TextView appr;
        TextView target;
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
        TrackRecordHolder holder;
        if(convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_track_record, parent, false);
            holder=new TrackRecordHolder();
            holder.bulan=(TextView) convertView.findViewById(R.id.tvBulanTrackRecord);
            holder.appr=(TextView) convertView.findViewById(R.id.tvApprTrackRecord);
            holder.target=(TextView) convertView.findViewById(R.id.tvReturTrackRecord);
            holder.presentasi=(TextView) convertView.findViewById(R.id.tvPresentasiTrackRecord);
            convertView.setTag(holder);
        }
        else {
            holder=(TrackRecordHolder) convertView.getTag();
        }
        holder.bulan.setText(trArrayList.get(position).getBulan());
        holder.appr.setText(IndoCurrencyFormat.parseByThousand(trArrayList.get(position).getPrice()/1000));
        holder.target.setText(IndoCurrencyFormat.parseByThousand(300000000/1000));
        holder.presentasi.setText(IndoCurrencyFormat.percentage((double)trArrayList.get(position).getPrice()/3000000));
        return convertView;

    }

}