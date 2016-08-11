package com.kreators.crtoolv1.Commons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.kreators.crtoolv1.Activity.HomeActivity;
import com.kreators.crtoolv1.Activity.LoginActivity;

import java.util.HashMap;

/**
 * Created by Julio Anthony Leonar on 8/11/2016.
 */

public class SessionManager {
    SharedPreferences pref;
    Editor editor;
    Context _context;

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(Protocol.PREF_NAME, Protocol.PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(int crID, String name){
        editor.putBoolean(Protocol.IS_LOGIN, true);
        editor.putString(Protocol.USERID, String.valueOf(crID));
        editor.putString(Protocol.USERNAME, name);
        editor.commit();
    }

    public void checkLoginSession(LoginActivity loginActivity){
        if(this.isLoggedIn()){
            Intent i = new Intent(_context, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
            loginActivity.finish();
        }
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(Protocol.IS_LOGIN,false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(Protocol.USERID, pref.getString(Protocol.USERID, null));
        user.put(Protocol.USERNAME, pref.getString(Protocol.USERNAME,null));
        return user;
    }


}