package com.kreators.crtoolv1.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kreators.crtoolv1.Commons.Constant;
import com.kreators.crtoolv1.Commons.SessionManager;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordProfileFragment extends Fragment {

    private View view;
    private VolleyManager volleyManager;
    private ProgressDialog pd;
    private SessionManager sessionManager;
    private EditText etPasswdLama,etPasswdBaru,etConfirmPasswdBaru;
    private Button btnUpdatePasswd;
    private HashMap<String, String> user = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_change_password_profile, container, false);
        initialization();
        return view;
    }

    private void initialization() {
        getActivity().setTitle(Constant.fragmentTitleEditProfile);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        volleyManager = VolleyManager.getInstance(getActivity().getApplicationContext());
        user = sessionManager.getUserDetails();
        etPasswdLama = (EditText) view.findViewById(R.id.etPasswdLama);
        etPasswdBaru= (EditText) view.findViewById(R.id.etPasswdBaru);
        etConfirmPasswdBaru = (EditText) view.findViewById(R.id.etConfirmPasswdBaru);
        btnUpdatePasswd = (Button) view.findViewById(R.id.btnUpdatePasswd);
        pd = new ProgressDialog(getActivity());
        pd.setMessage(Constant.msgDialog);
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        btnUpdatePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePasswd();
            }
        });
    }

    private void updatePasswd() {
        pd.setTitle(Constant.updateDialog);
        pd.show();
        if (pd != null) {
            pd.dismiss();
        }

        Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_SHORT).show();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(ChangePasswordProfileFragment.this);
        trans.commit();
        manager.popBackStack();
        ProfileFragment profileFragment=  new ProfileFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileActivity,profileFragment).commit();

    }
}
