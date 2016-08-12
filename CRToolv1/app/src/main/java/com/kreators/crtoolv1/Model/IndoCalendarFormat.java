package com.kreators.crtoolv1.Model;

import java.util.Calendar;

/**
 * Created by Julio Anthony Leonar on 8/3/2016.
 */
public class IndoCalendarFormat {
    public static final String[] days_short = {"Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab"};
    public static final String[] days = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
    public static final String[] months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    public static final String[] months_short = {"Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agt", "Sept", "Okt", "Nov", "Des"};

    public static String getDayName(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return days[(dayOfWeek - 1)];
    }

    public static String getShortDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return days_short[(dayOfWeek - 1)];
    }

    public static String getMonthName(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        final int month = calendar.get(Calendar.MONTH);
        return months[month];
    }

    public static String getMonthSimpleNameFromIndex (int idx) {
        return months_short[idx-1];
    }

    public static String getMonthNameFromIndex (int idx) {
        return months[idx-1];
    }


    public static int getMonthIndex(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        final int month = calendar.get(Calendar.MONTH);
        return month+1;
    }

    public static int getMonthIndexFromName(String name) {
        int num;
        for(num=0;num<months.length;num++) {
            if(name == months[num]) return num+1;
        }
        return 0;
    }

    public static String getDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        final int month = calendar.get(Calendar.MONTH);
        return calendar.get(Calendar.DAY_OF_MONTH) + " " + months[month] + " " + calendar.get(Calendar.YEAR);
    }

    public static String getFullDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        final int month = calendar.get(Calendar.MONTH);
        return days[(dayOfWeek - 1)] + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + months[month] + " " + calendar.get(Calendar.YEAR);
    }

    public static String getDateMouthShort(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        final int month = calendar.get(Calendar.MONTH);
        return calendar.get(Calendar.DAY_OF_MONTH) + " " + months_short[month];
    }
}
