package com.kreators.crtoolv1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kreators.crtoolv1.R;

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
