package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Fragment.ChangePasswordProfileFragment;
import com.kreators.crtoolv1.Fragment.EditProfileFragment;
import com.kreators.crtoolv1.Fragment.ProfileFragment;
import com.kreators.crtoolv1.R;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.ProfileMainListener{

    private HashMap<String, String> profileData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileData = (HashMap<String, String>)getIntent().getSerializableExtra(Protocol.CRProfile);

        ProfileFragment profileFragment=  new ProfileFragment();

        Bundle b = new Bundle();
        b.putSerializable(Protocol.CRProfile, profileData);
        profileFragment.setArguments(b);

        getSupportFragmentManager().beginTransaction().add(R.id.profileActivity, profileFragment).commit();
    }

    public void onEditProfileButtonClick () {
        EditProfileFragment editProfileFragment =  new EditProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.profileActivity, editProfileFragment).addToBackStack(null).commit();
    }

    public void onChangePasswordButtonClick () {
        ChangePasswordProfileFragment changePasswordProfileFragment =  new ChangePasswordProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.profileActivity, changePasswordProfileFragment).addToBackStack(null).commit();
    }
}
