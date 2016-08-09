package com.kreators.crtoolv1.Model;

import java.io.Serializable;

/**
 * Created by Julio Anthony Leonar on 8/9/2016.
 */
public class TrackRecord implements Serializable {

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getAppr() {
        return appr;
    }

    public void setAppr(String appr) {
        this.appr = appr;
    }

    public String getRetur() {
        return retur;
    }

    public void setRetur(String retur) {
        this.retur = retur;
    }

    public String getPresentasi() {
        return presentasi;
    }

    public void setPresentasi(String presentasi) {
        this.presentasi = presentasi;
    }

    private String bulan;
    private String appr;
    private String retur;
    private String presentasi;

    public TrackRecord(String bulan, String appr, String retur, String presentasi) {
        this.bulan = bulan;
        this.appr = appr;
        this.retur = retur;
        this.presentasi = presentasi;
    }
}
