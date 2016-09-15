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
import android.widget.Toast;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Model.IndoCalendarFormat;
import com.kreators.crtoolv1.R;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReportMainFragment extends Fragment {
    private ReportMainListener activityCallback;
    private View view;
    private Button btnFrom, btnTo, btnTR, btnSO;
    private Date date1, date2;
    private long today,date1long, date2long;
    private Calendar toDate, fromDate, pastFiveYear, todayDate;
    private static final SimpleDateFormat dateStandartFormatter = new SimpleDateFormat(Constant.SYSTEM_DATE_STANDART, Locale.US);
    private String dateFrom, dateTo;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report_main, container, false);
        getActivity().setTitle("Report");
        bind();
        initialization();
        return view;
    }

    private void bind() {
        btnFrom = (Button) view.findViewById(R.id.btnDateFrom);
        btnTo = (Button) view.findViewById(R.id.btnDateTo);
        btnTR = (Button) view.findViewById(R.id.btnReportTrackRecord);
        btnSO = (Button) view.findViewById(R.id.btnReportSalesOut);
    }

    private void initialization() {
        fromDate = Calendar.getInstance();
        toDate = Calendar.getInstance();
        pastFiveYear = Calendar.getInstance();
        todayDate = Calendar.getInstance();

        fromDate.add(Calendar.DATE,-1);
        pastFiveYear.add(Calendar.YEAR,-5);
        todayDate.add(Calendar.DATE,1);
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
            public void onClick(View v) {
                showCalendarInDialog(btnFrom, date1, true);
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
                dateFrom = dateStandartFormatter.format(new Date(date1.getTime()));
                dateTo =dateStandartFormatter.format(new Date(date2.getTime()));
                reportTrackRecordButtonClicked(dateFrom,dateTo);
            }
        });
        btnSO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dateFrom = dateStandartFormatter.format(new Date(date1.getTime()));
                dateTo =dateStandartFormatter.format(new Date(date2.getTime()));

                if(btnTo.isEnabled()) reportSalesOutButtonClicked(dateFrom,dateTo); else{
                    Toast.makeText(getActivity(), Constant.selectDateTo, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showCalendarInDialog(final Button btn, final Date date, final boolean isFrom) {
        final Dialog dg = new Dialog(getActivity(), R.style.FullscreenAppCompatDialogTheme);
        dg.setContentView(R.layout.dialog_calendar);
        final CalendarPickerView dialogView = (CalendarPickerView) dg.findViewById(R.id.calendarView);
        dialogView.setCustomDayView(new DefaultDayViewAdapter());
        if(isFrom) {
            dialogView.init(pastFiveYear.getTime(),toDate.getTime())
                    .inMode(CalendarPickerView.SelectionMode.SINGLE)
                    .withSelectedDate(new Date(fromDate.getTimeInMillis()));
        } else {
            dialogView.init(fromDate.getTime(), todayDate.getTime())
                    .inMode(CalendarPickerView.SelectionMode.SINGLE)
                    .withSelectedDate(new Date(toDate.getTimeInMillis()));
        }
        Button btnOK = (Button) dg.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFrom) {
                    fromDate.setTime(dialogView.getSelectedDate());
                    btnTo.setEnabled(true);
                } else {
                    toDate.setTime(dialogView.getSelectedDate());
                }

                if(fromDate.getTimeInMillis() != toDate.getTimeInMillis()) {
                    btn.setText(IndoCalendarFormat.getFullDate(dialogView.getSelectedDate().getTime()));
                    date.setTime(dialogView.getSelectedDate().getTime());
                    dg.dismiss();
                } else {
                    Toast.makeText(getActivity(), Constant.dontSelectSameDate, Toast.LENGTH_SHORT).show();
                }
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


    public void reportTrackRecordButtonClicked(String dateFrom, String dateTo) {
        activityCallback.onReportTrackRecordButtonClick(dateFrom, dateTo);
    }

    public void reportSalesOutButtonClicked(String dateFrom, String dateTo) {
        activityCallback.onReportSalesOutButtonClick(dateFrom,dateTo);
    }

    public interface ReportMainListener {
        void onReportTrackRecordButtonClick(String dateFrom, String dateTo);
        void onReportSalesOutButtonClick(String dateFrom, String dateTo);
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
