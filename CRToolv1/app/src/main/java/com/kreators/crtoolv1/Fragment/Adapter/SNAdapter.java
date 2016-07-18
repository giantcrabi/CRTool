package com.kreators.crtoolv1.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kreators.crtoolv1.Model.SerialNumber;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;

/**
 * Created by Julio Anthony Leonar on 7/18/2016.
 */
public class SNAdapter extends BaseAdapter implements Filterable {

    public Context context;
    public ArrayList<SerialNumber> snArrayList;
    public ArrayList<SerialNumber> orig;

    public SNAdapter(Context context, ArrayList<SerialNumber> employeeArrayList) {
        super();
        this.context = context;
        this.snArrayList = employeeArrayList;
    }


    public class EmployeeHolder
    {
        TextView name;
        TextView age;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<SerialNumber> results = new ArrayList<SerialNumber>();
                if (orig == null)
                    orig = snArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final SerialNumber g : orig) {
                            if (g.getDate().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                snArrayList = (ArrayList<SerialNumber>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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
            holder.age=(TextView) convertView.findViewById(R.id.txtSN);
            convertView.setTag(holder);
        }
        else {
            holder=(EmployeeHolder) convertView.getTag();
        }

        holder.name.setText(snArrayList.get(position).getDate());
        holder.age.setText(String.valueOf(snArrayList.get(position).getSN()));

        return convertView;

    }

}