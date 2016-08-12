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

    //ReportProtocol
    public static final String DATE_PARAMETER = "dateParameter";
    public static final String DATE_FROM = "datefrom";
    public static final String DATE_TO = "dateto";

    //SalesOutReportProtocol
    public static final String SO_REPORT = "salesOutREport";
    public static final String SN = "SN";
    public static final String SN_OUTLET_NAME = "Name";
    public static final String SN_DATE ="RegDate";
    public static final String SN_ITEM_DESC ="ItemDesc";
    public static final String SN_STATUS ="InctvStatus";
    public static final String PRICE="Price";

    //Outlet
    public static final String OUTLETID = "idoutlet";
    public static final String OUTLETNAME = "outletName";
    public static final String NEAREST = "nearestOutlet";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lng";

    //Date
    public static final String CUR_DATE = "date";
    public static final String CUR_DATE_TIME = "datetime";
}
