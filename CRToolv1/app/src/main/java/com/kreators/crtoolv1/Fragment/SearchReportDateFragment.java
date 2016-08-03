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
public class SearchReportDateFragment extends Fragment {

    SearchReportDateListener activityCallback;
    private Button btnFrom, btnTo, btnFind;
    private Date date1, date2;
    private long today;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search_report_date, container, false);
        btnFrom = (Button) v.findViewById(R.id.btnDateFrom);
        btnTo = (Button) v.findViewById(R.id.btnDateTo);
        btnFind = (Button) v.findViewById(R.id.btnFindHistory);
        today = Calendar.getInstance().getTimeInMillis();
        date1 = new Date(today);
        date2 = new Date(today);
        return v;
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

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchReportDateButtonClicked();
            }
        });

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchReportDateListener) {
            activityCallback = (SearchReportDateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReportMainListener");
        }
    }
    public void onSearchReportDateButtonClicked() {
        activityCallback.onSearchReportDateButtonClick();
    }

    public interface SearchReportDateListener {
        void onSearchReportDateButtonClick();
    }


}









