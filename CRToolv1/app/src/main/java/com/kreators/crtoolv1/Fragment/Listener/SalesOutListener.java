package com.kreators.crtoolv1.Fragment.Listener;

import me.dm7.barcodescanner.zbar.Result;

/**
 * Created by DELL on 29/07/2016.
 */
public interface SalesOutListener {
    void onScanButtonClick();
    void handleResult(Result result);
}
