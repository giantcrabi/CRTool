package com.kreators.crtoolv1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kreators.crtoolv1.R;

/**
 * Created by DELL on 14/06/2016.
 */
public class SalesOutInputFragment extends Fragment {

    private TextView textSN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_out_input, container, false);

        textSN = (TextView) view.findViewById(R.id.textSN);

        Bundle bundle = getArguments();

        if(bundle != null) {
            textSN.setText(bundle.getString("Content"));
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        textSN.setText("");
    }
}
