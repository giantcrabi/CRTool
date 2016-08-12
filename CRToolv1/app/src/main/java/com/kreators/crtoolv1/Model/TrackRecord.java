package com.kreators.crtoolv1.Model;

import java.io.Serializable;

/**
 * Created by Julio Anthony Leonar on 8/9/2016.
 */
public class TrackRecord implements Serializable {
    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TrackRecord(long price, String bulan, int status) {
        this.price = price;
        this.bulan = bulan;
        this.status = status;
    }

    private long price;
    private String bulan;
    private int status;

    public TrackRecord() {
    }
}
