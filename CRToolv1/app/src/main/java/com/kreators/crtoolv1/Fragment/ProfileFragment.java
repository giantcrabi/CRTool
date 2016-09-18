package com.kreators.crtoolv1.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private ProfileMainListener activityCallback;
    private View view;
    private TextView tvCRName, tvCREmail, tvCRPhone, tvBankName, tvBankAccountName, tvBankAccountNumber;
    private Button btnEditProfile, btnChangePwd;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileMainListener) {
            activityCallback = (ProfileMainListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReportMainListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        initialization();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileButtonClicked();
            }
        });
        btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordButtonClicked();
            }
        });
    }

    private void initialization() {
        getActivity().setTitle(Constant.fragmentTitleProfile);
        tvCRName = (TextView) view.findViewById(R.id.tvCRName);
        tvCREmail= (TextView) view.findViewById(R.id.tvCREmail);
        tvCRPhone = (TextView) view.findViewById(R.id.tvCRPhone);
        tvBankName = (TextView) view.findViewById(R.id.tvCRBankName);
        tvBankAccountName   = (TextView) view.findViewById(R.id.tvCRBankAccountName);
        tvBankAccountNumber = (TextView) view.findViewById(R.id.tvBankAccountNumber);

        Bundle bundle = getArguments();
        if(bundle != null) {
            HashMap<String, String> profileData = (HashMap<String, String>)bundle.getSerializable(Protocol.CRProfile);
            tvCRName.setText(tvCRName.getText() + ": " + profileData.get(Protocol.CRName));
            tvCREmail.setText(tvCREmail.getText() + ": " + profileData.get(Protocol.CREmail));
            tvCRPhone.setText(tvCRPhone.getText() + ": " + profileData.get(Protocol.CRHP));
            tvBankAccountName.setText(tvBankAccountName.getText() + ": " + profileData.get(Protocol.CRBankAccountName));
            tvBankName.setText(tvBankName.getText() + ": " + profileData.get(Protocol.CRBankName));
            tvBankAccountNumber.setText(tvBankAccountNumber.getText() + ": " + profileData.get(Protocol.CRBankAccountNo));
        }

        btnEditProfile = (Button) view.findViewById(R.id.fragment_profile_button_edit);
        btnChangePwd = (Button) view.findViewById(R.id.fragment_profile_button_change_password);
    }

    public void editProfileButtonClicked () {
        activityCallback.onEditProfileButtonClick();
    }

    public void changePasswordButtonClicked() {
        activityCallback.onChangePasswordButtonClick();
    }

    public interface ProfileMainListener {

        void onEditProfileButtonClick();
        void onChangePasswordButtonClick();
    }

}
