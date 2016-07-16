package com.kreators.crtoolv1.Fragment.Dialog;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.kreators.crtoolv1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectOutletDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    ArrayAdapter<String> adapter;
    ArrayList<String> mOutlets;

//    String[] players = {"Outlet Bumi Marina","Outlet Pakuwon City","Outlet Galaxy Mall","Outlet ITS",
//            "Outlet A", "Outlet B", "Outlet C","Outlet D", "Outlet E",
//            "Outlet F", "Outlet G", "Outlet H","Outlet I", "Outlet J"};

    public static SelectOutletDialogFragment newInstance(List<String> nearestOutlet) {
        SelectOutletDialogFragment f = new SelectOutletDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putStringArrayList("listNearestOutlet", (ArrayList) nearestOutlet);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOutlets = getArguments().getStringArrayList("listNearestOutlet");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_outlet_dialog, container, false);

        getDialog().setTitle("Select Outlet");

        ListView lv = (ListView) rootView.findViewById(R.id.lvListViewOutlet);
        SearchView sv = (SearchView) rootView.findViewById(R.id.svSearchOutlet);

        String[] outlets = new String[mOutlets.size()];
        outlets = mOutlets.toArray(outlets);

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, outlets);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

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

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        String item = adapter.getItemAtPosition(position).toString();
        MyDialogFragmentListener activity = (MyDialogFragmentListener) getActivity();
        activity.onReturnValue(item);
        dismiss();
    }

    public interface MyDialogFragmentListener {
        void onReturnValue(String loc);
    }

}
