package com.kreators.crtoolv1.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Commons.SessionManager;
import com.kreators.crtoolv1.Commons.Url;
import com.kreators.crtoolv1.Network.GetVolleyRequest;
import com.kreators.crtoolv1.Network.VolleyListener;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.Network.VolleyRequest;
import com.kreators.crtoolv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private VolleyManager volleyManager;
    private ProgressDialog pd;
    private EditText editTextUsername,editTextPassword;
    private String username,password;
    private Button buttonLogin;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
        session.checkLoginSession(LoginActivity.this);
    }

    private void checkLogin() {
        pd.setTitle(Constant.loginDialog);
        pd.show();
        GetVolleyRequest request = new GetVolleyRequest(Url.LOG_IN);
        request.putParams(Protocol.USERNAME,username);
        request.putParams(Protocol.PASSWORD,password);
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                try {
                    JSONObject response;
                    int crID=0;
                    boolean status=false;
                    String message="";
                    for(int i = 0; i < result.length(); i++) {
                        response = result.getJSONObject(i);
                        message = response.getString("message");
                        status = response.getBoolean("status");
                        if(response.has("ID")) crID = response.getInt("ID");
                    }
                    if (pd != null) {
                        pd.dismiss();
                    }
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                    if(status) {
                        session.createLoginSession(crID, username);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyRequest request, String errorMessage) {
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                if (pd != null) {
                    pd.dismiss();
                }
            }
        });
        volleyManager.createRequest(request,Protocol.POST);
    }

    @Override
    public void onClick(View v) {
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();
        if (!username.equals("") && !password.equals("")) {
            checkLogin();
        } else {
            Toast.makeText(LoginActivity.this, "Fill Username or Password", Toast.LENGTH_LONG).show();
        }
    }

    public void initialization() {
        session = new SessionManager(getApplicationContext());
        editTextUsername = (EditText) findViewById(R.id.usernameLogin);
        editTextPassword = (EditText) findViewById(R.id.passwordLogin);
        volleyManager = VolleyManager.getInstance(getApplicationContext());
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
    }
}

