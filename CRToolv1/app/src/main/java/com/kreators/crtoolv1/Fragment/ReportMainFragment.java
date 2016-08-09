package com.kreators.crtoolv1.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kreators.crtoolv1.Model.IndoCalendarFormat;
import com.kreators.crtoolv1.R;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportMainFragment extends Fragment {

    ReportMainListener activityCallback;
    private Button btnFrom, btnTo;
    private Date date1, date2;
    private long today;

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
        btnFrom = (Button) view.findViewById(R.id.btnDateFrom);
        btnTo = (Button) view.findViewById(R.id.btnDateTo);
        today = Calendar.getInstance().getTimeInMillis();
        date1 = new Date(today);
        date2 = new Date(today);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarInDialog(btnFrom, date1);
            }
        });

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarInDialog(btnTo, date2);
            }
        });
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

    private void showCalendarInDialog(final Button btn, final Date date) {
        final Dialog dg = new Dialog(getActivity(), R.style.FullscreenAppCompatDialogTheme);
        dg.setContentView(R.layout.dialog_calendar);
        final CalendarPickerView dialogView = (CalendarPickerView) dg.findViewById(R.id.calendarView);
        final Calendar prevYear = Calendar.getInstance();
        final Calendar nextDay = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -5);
        nextDay.add(Calendar.DATE, 1);
        dialogView.setCustomDayView(new DefaultDayViewAdapter());
        dialogView.init(prevYear.getTime(),nextDay.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date(today));
        Button btnOK = (Button) dg.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText(IndoCalendarFormat.getFullDate(dialogView.getSelectedDate().getTime()));
                date.setTime(dialogView.getSelectedDate().getTime());
                dg.dismiss();
            }
        });
        dg.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                dialogView.fixDialogDimens();
            }
        });
        dg.show();
    }


}
