package com.kreators.crtoolv1.Fragment.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreators.crtoolv1.R;

/**
 * Created by Julio Anthony Leonar on 8/5/2016.
 */
public class ReportSalesOutDialogFragment extends DialogFragment {

    String mInput;

    public static ReportSalesOutDialogFragment newInstance(String input) {
        ReportSalesOutDialogFragment f = new ReportSalesOutDialogFragment();
        Bundle args = new Bundle();
        args.putString("ReportSalesOut", input);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInput = getArguments().getString("ReportSalesOut");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_sales_out_dialog, container, false);
        getDialog().setTitle(mInput);
        return rootView;
    }

}
