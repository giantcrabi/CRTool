package com.kreators.crtoolv1.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private View view;
    private VolleyManager volleyManager;
    private ProgressDialog pd;
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
        volleyManager = VolleyManager.getInstance(getActivity().getApplicationContext());
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
        pd = new ProgressDialog(getActivity());
        pd.setMessage(Constant.msgDialog);
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void saveProfile() {
        pd.setTitle(Constant.updateDialog);
        pd.show();
        GetVolleyRequest request = new GetVolleyRequest(Url.PROFILE);
        request.putParams(Protocol.CRID,user.get(Protocol.USERID));
        request.putParams(Protocol.CRName,etCRName.getText().toString());
        request.putParams(Protocol.CREmail,etCREmail.getText().toString());
        request.putParams(Protocol.CRHP,etCRPhone.getText().toString());
        request.putParams(Protocol.CRBankAccountNo,etBankAccountNumber.getText().toString());
        request.putParams(Protocol.CRBankName,etBankName.getText().toString());
        request.putParams(Protocol.CRBankAccountName,etBankAccountName.getText().toString());
        Log.d("","");
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                try {
                    JSONObject response;
                    String message;
                    boolean status;
                    response = result.getJSONObject(0);
                    message = response.getString(Protocol.MESSAGE);
                    status =  response.getBoolean(Protocol.STATUS);

                    if (pd != null) {
                        pd.dismiss();
                    }

                    if(status) {
                        String name,email,phone,bankAccountName,bankName,bankAccountNo;
                        name = etCRName.getText().toString();
                        email = etCREmail.getText().toString();
                        phone = etCRPhone.getText().toString();
                        bankAccountName = etBankAccountName.getText().toString();
                        bankName = etBankName.getText().toString();
                        bankAccountNo = etBankAccountNumber.getText().toString();
                        sessionManager.setUserProfile(name,email,phone,bankAccountName,bankName,bankAccountNo);

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction trans = manager.beginTransaction();
                        trans.remove(EditProfileFragment.this);
                        trans.commit();
                        manager.popBackStack();
                        ProfileFragment profileFragment=  new ProfileFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileActivity,profileFragment).commit();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyRequest request, String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                if (pd != null) {
                    pd.dismiss();
                }
            }
        });
        volleyManager.createRequest(request,Protocol.POST);
    }

}
