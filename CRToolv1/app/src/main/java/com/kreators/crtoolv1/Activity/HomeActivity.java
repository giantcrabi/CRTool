package com.kreators.crtoolv1.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kreators.crtoolv1.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void doCheckIn(View view) {
        Intent intent = new Intent(this, CheckInActivity.class);
        startActivity(intent);
    }

    public void viewReport(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }
}
