package com.kreators.crtoolv1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by DELL on 14/06/2016.
 */
public class SalesOutMainFragment extends Fragment{

    SalesOutMainListener activityCallback;

    public interface SalesOutMainListener {
        public void onInputButtonClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SalesOutMainListener) {
            activityCallback = (SalesOutMainListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SalesOutMainListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_out_main, container, false);

        final Button doInputSN =
                (Button) view.findViewById(R.id.doInputSN);
        doInputSN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputButtonClicked();
            }
        });

        return view;
    }

    public void inputButtonClicked () {
        activityCallback.onInputButtonClick();
    }
}
