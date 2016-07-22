package com.kreators.crtoolv1.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kreators.crtoolv1.R;

/**
 * Created by DELL on 14/06/2016.
 */
public class SalesOutMainFragment extends Fragment {

    SalesOutMainListener activityCallback;

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

        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("INPUT");
        if(fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        final Button doInputSN =
                (Button) view.findViewById(R.id.doInputSN);
        doInputSN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputButtonClicked();
            }
        });

        final Button scanSN =
                (Button) view.findViewById(R.id.scanSN);
        scanSN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scanButtonClicked();
            }
        });

        return view;
    }

    public void inputButtonClicked() {
        activityCallback.onInputButtonClick();
    }

    public void scanButtonClicked() {
        activityCallback.onScanButtonClick();
    }

    public interface SalesOutMainListener {
        void onInputButtonClick();

        void onScanButtonClick();
    }
}
