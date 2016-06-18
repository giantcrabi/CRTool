package com.kreators.crtoolv1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kreators.crtoolv1.Fragment.CheckInMainFragment;
import com.kreators.crtoolv1.R;

public class CheckInActivity extends AppCompatActivity implements CheckInMainFragment.CheckInMainListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        if (findViewById(R.id.checkInActivity) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            CheckInMainFragment checkInMainFragment= new CheckInMainFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.checkInActivity, checkInMainFragment).commit();
        }

    }


    public void onSelectOutletButtonClick() {
    }

    public void onCheckInButtonClick(){
        Intent intent = new Intent(this, SalesOutActivity.class);
        startActivity(intent);
    }
}
