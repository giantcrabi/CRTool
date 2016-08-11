package com.kreators.crtoolv1.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Julio Anthony Leonar on 8/11/2016.
 */
public class DateParameter implements Parcelable{
    String dateFrom;
    String dateTo;

    public DateParameter(String dateFrom, String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    protected DateParameter(Parcel in) {
        dateFrom = in.readString();
        dateTo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateFrom);
        dest.writeString(dateTo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DateParameter> CREATOR = new Creator<DateParameter>() {
        @Override
        public DateParameter createFromParcel(Parcel in) {
            return new DateParameter(in);
        }

        @Override
        public DateParameter[] newArray(int size) {
            return new DateParameter[size];
        }
    };

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
