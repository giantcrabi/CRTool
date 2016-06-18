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
 * A simple {@link Fragment} subclass.
 */
public class CheckInMainFragment extends Fragment{

    CheckInMainListener  activityCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_check_in_main, container, false);

        final Button btnCO = (Button) view.findViewById(R.id.btnChooseOutlet);
        btnCO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSelectOutletButtonClicked();
            }
        });

        final Button btnCI = (Button) view.findViewById(R.id.btnCheckIn);
        btnCI.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCheckInButtonClicked();
            }
        });

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CheckInMainListener) {
            activityCallback = (CheckInMainListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReportMainListener");
        }
    }

    public void onSelectOutletButtonClicked() {
        activityCallback.onSelectOutletButtonClick();
    }
    public void onCheckInButtonClicked() { activityCallback.onCheckInButtonClick();}


    public interface CheckInMainListener {
        void onSelectOutletButtonClick();

        void onCheckInButtonClick();
    }

}
