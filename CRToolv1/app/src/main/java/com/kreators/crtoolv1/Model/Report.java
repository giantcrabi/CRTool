package com.kreators.crtoolv1.Model;

import java.io.Serializable;

/**
 * Created by Julio Anthony Leonar on 7/18/2016.
 */
public class Report implements Serializable {

    private String date;
    private String SN;

    public Report(String date, String SN){
        this.date = date;
        this.SN = SN;
    }

    public String getDate(){
        return date;
    }

    public String getSN(){
        return SN;
    }
}