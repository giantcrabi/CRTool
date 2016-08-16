package com.kreators.crtoolv1.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kreators.crtoolv1.Model.Report;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;

/**
 * Created by Julio Anthony Leonar on 7/18/2016.
 */
public class HistorySalesOutAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<Report> snArrayList;

    public HistorySalesOutAdapter(Context context, ArrayList<Report> historyArrayList) {
        super();
        this.context = context;
        this.snArrayList = historyArrayList;
    }


    public class HistoryHolder
    {
        TextView name;
        TextView size;
    }
    @Override
    public int getCount() {
        return snArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return snArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryHolder holder;
        if(convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_histroy_sales_out_row, parent, false);
            holder=new HistoryHolder();
            holder.name=(TextView) convertView.findViewById(R.id.txtName);
            holder.size=(TextView) convertView.findViewById(R.id.txtSize);
            convertView.setTag(holder);
        }
        else {
            holder=(HistoryHolder) convertView.getTag();
        }

        holder.name.setText(snArrayList.get(position).getDate());
        holder.size.setText(snArrayList.get(position).getSN());

        return convertView;

    }

}