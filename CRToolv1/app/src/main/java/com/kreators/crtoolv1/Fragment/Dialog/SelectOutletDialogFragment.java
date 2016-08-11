package com.kreators.crtoolv1.Fragment.Dialog;


import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.kreators.crtoolv1.Fragment.Adapter.OutletAdapter;
import com.kreators.crtoolv1.Model.Outlet;
import com.kreators.crtoolv1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectOutletDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    private OutletAdapter outletAdapter;
    private List<Outlet> mOutlets;

    public static SelectOutletDialogFragment newInstance(List<Outlet> nearestOutlet) {
        SelectOutletDialogFragment f = new SelectOutletDialogFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList("listNearestOutlet", (ArrayList<? extends Parcelable>) nearestOutlet);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOutlets = getArguments().getParcelableArrayList("listNearestOutlet");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_outlet_dialog, container, false);

        getDialog().setTitle("Select Outlet");

        ListView lv = (ListView) rootView.findViewById(R.id.lvListViewOutlet);
        SearchView sv = (SearchView) rootView.findViewById(R.id.svSearchOutlet);

        outletAdapter = new OutletAdapter(getActivity(), mOutlets);
        lv.setAdapter(outletAdapter);
        lv.setOnItemClickListener(this);

        sv.setQueryHint("Search..");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String txt) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String txt) {
                outletAdapter.getFilter().filter(txt);
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        Outlet item = (Outlet) adapter.getItemAtPosition(position);
        MyDialogFragmentListener activity = (MyDialogFragmentListener) getActivity();
        activity.onReturnValue(item);
        dismiss();
    }

    public interface MyDialogFragmentListener {
        void onReturnValue(Outlet outlet);
    }

}
