package com.kreators.crtoolv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CheckInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
    }

    public void checkIn(View view){
        Intent intent = new Intent(this, SalesOutActivity.class);
        startActivity(intent);
    }
}
