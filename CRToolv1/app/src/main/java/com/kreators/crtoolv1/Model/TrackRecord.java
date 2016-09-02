package com.kreators.crtoolv1.Model;

import java.io.Serializable;

/**
 * Created by Julio Anthony Leonar on 8/9/2016.
 */
public class TrackRecord implements Serializable {

    private long price;
    private String bulan;
    private int status;
    private int submitted;
    private int received;
    private int approved;
    private int retur;

    public TrackRecord(long price, String bulan, int submitted,int received, int approved, int retur) {
        this.price = price;
        this.bulan = bulan;
        this.submitted = submitted;
        this.received = received;
        this.approved = approved;
        this.retur = retur;
    }

    public TrackRecord(){

    }
    public TrackRecord(long price, String bulan, int status) {
        this.price = price;
        this.bulan = bulan;
        this.status = status;
    }

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

    public int getSubmitted() {
        return submitted;
    }

    public void setSubmitted(int submitted) {
        this.submitted = submitted;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getRetur() {
        return retur;
    }

    public void setRetur(int retur) {
        this.retur = retur;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }
}
