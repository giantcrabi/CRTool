package com.kreators.crtoolv1.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DELL on 11/08/2016.
 */
public class Outlet implements Parcelable {

    int outletID;
    String outletName;

    public Outlet() {}

    public Outlet(int outletID, String outletName) {
        this.outletID = outletID;
        this.outletName = outletName;
    }

    protected Outlet(Parcel in) {
        outletID = in.readInt();
        outletName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(outletID);
        dest.writeString(outletName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Outlet> CREATOR = new Creator<Outlet>() {
        @Override
        public Outlet createFromParcel(Parcel in) {
            return new Outlet(in);
        }

        @Override
        public Outlet[] newArray(int size) {
            return new Outlet[size];
        }
    };

    public int getOutletID() {
        return outletID;
    }

    public void setOutletID(int outletID) {
        this.outletID = outletID;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
}
