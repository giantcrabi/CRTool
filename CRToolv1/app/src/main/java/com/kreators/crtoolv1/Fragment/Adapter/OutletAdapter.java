package com.kreators.crtoolv1.Fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kreators.crtoolv1.Model.Outlet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 11/08/2016.
 */
public class OutletAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Outlet> outletList;
    private List<Outlet> outletListFiltered;

    public OutletAdapter(Context context, List<Outlet> outletList) {
        this.context = context;
        this.outletList = outletList;
        this.outletListFiltered = outletList;
    }

    public class OutletHolder
    {
        TextView name;
    }

    @Override
    public int getCount() {
        return outletListFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return outletListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OutletHolder holder;

        if(convertView == null) {
            convertView= LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new OutletHolder();
            holder.name = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        }
        else {
            holder = (OutletHolder) convertView.getTag();
        }

        holder.name.setText(outletListFiltered.get(position).getOutletName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();

                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = outletList;
                    results.count = outletList.size();
                }
                else
                {
                    List<Outlet> filteredOutlets = new ArrayList<>();
                    String filterString = charSequence.toString().toLowerCase();
                    String filterableString;

                    for(int i = 0; i < outletList.size(); i++){
                        filterableString = outletList.get(i).getOutletName();
                        if(filterableString.toLowerCase().contains(filterString)){
                            filteredOutlets.add(outletList.get(i));
                        }
                    }
                    results.values = filteredOutlets;
                    results.count = filteredOutlets.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results)
            {
                outletListFiltered = (List<Outlet>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
