package com.kreators.crtoolv1.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kreators.crtoolv1.R;

import java.util.ArrayList;

/**
 * Created by Julio Anthony Leonar on 7/18/2016.
 */
public class SNAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<String> snArrayList;
    public ArrayList<String> orig;

    public SNAdapter(Context context, ArrayList<String> employeeArrayList) {
        super();
        this.context = context;
        this.snArrayList = employeeArrayList;
    }


    public class EmployeeHolder
    {
        TextView name;
        TextView age;
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
        EmployeeHolder holder;
        if(convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_serial_number_row, parent, false);
            holder=new EmployeeHolder();
            holder.name=(TextView) convertView.findViewById(R.id.txtDate);
            convertView.setTag(holder);
        }
        else {
            holder=(EmployeeHolder) convertView.getTag();
        }

        holder.name.setText(snArrayList.get(position));

        return convertView;

    }

}