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

public class ReportMainFragment extends Fragment {
    private ReportMainListener activityCallback;
    private View view;
    private Button btnFrom, btnTo, btnTR, btnSO;
    private Date date1, date2;
    private long today,date1long, date2long;
    private Calendar toDate, fromDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report_main, container, false);
        bind();
        setDefaultLayout();
        return view;
    }

    private void bind() {
        btnFrom = (Button) view.findViewById(R.id.btnDateFrom);
        btnTo = (Button) view.findViewById(R.id.btnDateTo);
        btnTR = (Button) view.findViewById(R.id.btnReportTrackRecord);
        btnSO = (Button) view.findViewById(R.id.btnReportSalesOut);
    }

    private void setDefaultLayout() {
        fromDate = Calendar.getInstance();
        toDate = Calendar.getInstance();
        fromDate.add(Calendar.YEAR, -5);
        toDate.add(Calendar.DATE, 1);
        date1long = fromDate.getTimeInMillis();
        date2long = toDate.getTimeInMillis();
        today = Calendar.getInstance().getTimeInMillis();
        date1 = new Date(date1long);
        date2 = new Date(date2long);
        btnFrom.setText(IndoCalendarFormat.getFullDate(date1.getTime()));
        btnTo.setText(IndoCalendarFormat.getFullDate(date2.getTime()));
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showCalendarInDialog(btnFrom, date1, true);
            }
        });
        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarInDialog(btnTo, date2, false);
            }
        });
        btnTR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reportTrackRecordButtonClicked();
            }
        });
        btnSO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reportSalesOutButtonClicked();
            }
        });
    }

    private void showCalendarInDialog(final Button btn, final Date date, final boolean isFrom) {
        final Dialog dg = new Dialog(getActivity(), R.style.FullscreenAppCompatDialogTheme);
        dg.setContentView(R.layout.dialog_calendar);
        final CalendarPickerView dialogView = (CalendarPickerView) dg.findViewById(R.id.calendarView);

        dialogView.setCustomDayView(new DefaultDayViewAdapter());
        dialogView.init(fromDate.getTime(),toDate.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date(today));
        Button btnOK = (Button) dg.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText(IndoCalendarFormat.getFullDate(dialogView.getSelectedDate().getTime()));
                if(isFrom) {
                    fromDate.setTime(dialogView.getSelectedDate());
                    btnTo.setEnabled(true);
                }
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



}
