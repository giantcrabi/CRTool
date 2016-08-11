package com.kreators.crtoolv1.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Julio Anthony Leonar on 8/11/2016.
 */
public class SalesOutReport implements Parcelable {
    long SN;
    String outletName;
    String postDate;
    String itemDesc;
    int inctvStatus;

    public SalesOutReport(long SN, String outletName, String postDate, String itemDesc, int inctvStatus) {
        this.SN = SN;
        this.outletName = outletName;
        this.postDate = postDate;
        this.itemDesc = itemDesc;
        this.inctvStatus = inctvStatus;
    }

    protected SalesOutReport(Parcel in) {
        SN = in.readLong();
        outletName = in.readString();
        postDate = in.readString();
        itemDesc = in.readString();
        inctvStatus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(SN);
        dest.writeString(outletName);
        dest.writeString(postDate);
        dest.writeString(itemDesc);
        dest.writeInt(inctvStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SalesOutReport> CREATOR = new Creator<SalesOutReport>() {
        @Override
        public SalesOutReport createFromParcel(Parcel in) {
            return new SalesOutReport(in);
        }

        @Override
        public SalesOutReport[] newArray(int size) {
            return new SalesOutReport[size];
        }
    };

    public long getSN() {
        return SN;
    }

    public void setSN(long SN) {
        this.SN = SN;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public int getInctvStatus() {
        return inctvStatus;
    }

    public void setInctvStatus(int inctvStatus) {
        this.inctvStatus = inctvStatus;
    }
}
