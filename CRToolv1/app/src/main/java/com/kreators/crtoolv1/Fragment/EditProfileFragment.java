package com.kreators.crtoolv1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Commons.SessionManager;
import com.kreators.crtoolv1.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private View view;
    private SessionManager sessionManager;
    private EditText etCRName,etCREmail,etCRPhone,etBankName,etBankAccountName,etBankAccountNumber;
    private Button btnEditProfile;
    private HashMap<String, String> user = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        initialization();

        return view;
    }

    private void initialization() {
        getActivity().setTitle(Constant.fragmentTitleEditProfile);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        user = sessionManager.getUserDetails();

        etCRName = (EditText) view.findViewById(R.id.etCRName);
        etCREmail= (EditText) view.findViewById(R.id.etCREmail);
        etCRPhone = (EditText) view.findViewById(R.id.etCRPhone);
        etBankName = (EditText) view.findViewById(R.id.etCRBankName);
        etBankAccountName   = (EditText) view.findViewById(R.id.etCRBankAccountName);
        etBankAccountNumber = (EditText) view.findViewById(R.id.etCRBankAccountNumber);

        etCRName.setText(user.get(Protocol.CRName));
        etCREmail.setText(user.get(Protocol.CREmail));
        etCRPhone.setText(user.get(Protocol.CRHP));
        etBankAccountName.setText(user.get(Protocol.CRBankAccountName));
        etBankName.setText(user.get(Protocol.CRBankName));
        etBankAccountNumber.setText(user.get(Protocol.CRBankAccountNo));

        btnEditProfile = (Button) view.findViewById(R.id.btnEditProfile);

    }

}
