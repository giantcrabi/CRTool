package com.kreators.crtoolv1.Model;

/**
 * Created by Julio Anthony Leonar on 7/18/2016.
 */
public class SerialNumber {

    private String date;
    private String SN;

    public SerialNumber(String date,String SN){
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