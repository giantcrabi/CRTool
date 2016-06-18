package com.kreators.crtoolv1.Fragment.Dialog;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.kreators.crtoolv1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectOutletDialogFragment extends DialogFragment{

    ArrayAdapter<String> adapter;
    String[] players = {"Outlet Bumi Marina","Outlet Pakuwon City","Outlet Galaxy Mall","Outlet ITS",
            "Outlet A", "Outlet B", "Outlet C","Outlet D", "Outlet E",
            "Outlet F", "Outlet G", "Outlet H","Outlet I", "Outlet J"};

    public SelectOutletDialogFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_outlet_dialog,null);

        getDialog().setTitle("Select Outlet");

        Button btn = (Button) rootView.findViewById(R.id.btnDismissOutletDialog);
        ListView lv = (ListView) rootView.findViewById(R.id.lvListViewOutlet);
        SearchView sv = (SearchView) rootView.findViewById(R.id.svSearchOutlet);

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,players);
        lv.setAdapter(adapter);

        sv.setQueryHint("Search..");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String txt) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String txt) {
                adapter.getFilter().filter(txt);
                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return rootView;

    }

}
