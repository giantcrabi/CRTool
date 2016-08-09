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
public class ReportMainFragment extends Fragment {

    ReportMainListener activityCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_main, container, false);


        final Button btnTR = (Button) view.findViewById(R.id.btnReportTrackRecord);
        btnTR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reportTrackRecordButtonClicked();
            }
        });


        final Button btnSO = (Button) view.findViewById(R.id.btnReportSalesOut);
        btnSO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reportSalesOutButtonClicked();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReportMainListener) {
            activityCallback = (ReportMainListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReportMainListener");
        }
    }

    public void reportTrackRecordButtonClicked() {
        activityCallback.onReportTrackRecordButtonClick();
    }

    public void reportSalesOutButtonClicked() {
        activityCallback.onReportSalesOutButtonClick();
    }

    public interface ReportMainListener {
        void onReportTrackRecordButtonClick();
        void onReportSalesOutButtonClick();
    }


}
