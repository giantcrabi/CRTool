package com.kreators.crtoolv1.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Fragment.ChangePasswordProfileFragment;
import com.kreators.crtoolv1.Fragment.EditProfileFragment;
import com.kreators.crtoolv1.Fragment.ProfileFragment;
import com.kreators.crtoolv1.R;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.ProfileMainListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ProfileFragment profileFragment=  new ProfileFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.profileActivity, profileFragment).commit();
    }

    public void onEditProfileButtonClick () {
        EditProfileFragment editProfileFragment =  new EditProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.profileActivity, editProfileFragment).addToBackStack(Constant.fragmentTitleEditProfile).commit();
    }

    public void onChangePasswordButtonClick () {
        ChangePasswordProfileFragment changePasswordProfileFragment =  new ChangePasswordProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.profileActivity, changePasswordProfileFragment).addToBackStack(Constant.fragmentTitleProfile).commit();
    }
}
