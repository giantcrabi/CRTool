package com.kreators.crtoolv1.Commons;

/**
 * Created by Julio Anthony Leonar on 8/10/2016.
 */
public class Protocol {

    public static final String FRAGMENT_TAG = "fragment_tag";

    //WebService
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";

    //Login protocol
    public static final String USERID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String PREF_NAME = "UserSession";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final    int PRIVATE_MODE = 0;

    //SN
    public static final String CONTENT = "content";

    //CR
    
    public static final String CRID = "idCR";
    public static final String CRName = "Name";
    public static final String CREmail = "Email";
    public static final String CRHP = "Handphone";
    public static final String CRBankName = "BankName";
    public static final String CRBankAccountNo =  "BankAccountNo";
    public static final String CRBankAccountName =  "BankAccountName";


    //ReportProtocol
    public static final String DATE_PARAMETER = "dateParameter";
    public static final String DATE_FROM = "datefrom";
    public static final String DATE_TO = "dateto";
    public static final String TRACK_RECORD_DETAILS = "detailTrackRecord";

    //SalesOutReportProtocol
    public static final String SO_REPORT = "salesOutReport";
    public static final String SN = "SN";
    public static final String SN_OUTLET_NAME = "OutletName";
    public static final String SN_DATE = "RegDate";
    public static final String SN_ITEM_DESC = "ItemDesc";
    public static final String SN_STATUS = "InctvStatus";
    public static final String PRICE = "SalesInPrice";

    //Outlet
    public static final String OUTLETID = "idoutlet";
    public static final String NEAREST = "nearestOutlet";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lng";

    //Date
    public static final String CUR_DATE = "date";
    public static final String CUR_DATE_TIME = "datetime";
}
