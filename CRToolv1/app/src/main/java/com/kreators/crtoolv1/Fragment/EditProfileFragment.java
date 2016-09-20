package com.kreators.crtoolv1.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.kreators.crtoolv1.Model.TrackRecord;
import com.kreators.crtoolv1.Network.GetVolleyRequest;
import com.kreators.crtoolv1.Network.VolleyListener;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.Network.VolleyRequest;
import com.kreators.crtoolv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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
        request.putParams(Protocol.CRID,);
        request.putParams(Protocol.CRName,);
        request.putParams(Protocol.USERID,);
        request.putParams(Protocol.USERID,);
        request.putParams(Protocol.USERID,);
        request.putParams(Protocol.USERID,);
        request.putParams(Protocol.USERID,);

        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                try {
                    JSONObject response;
                    TrackRecord trackRecord;
                    Long price;
                    Integer status;
                    String date;

                    for(int i = 0; i < result.length(); i++) {
                        response = result.getJSONObject(i);
                        price = response.getLong(Protocol.PRICE);
                        date = response.getString(Protocol.SN_DATE);
                        status = response.getInt(Protocol.SN_STATUS);
                        trackRecord = new TrackRecord(price,date,status);
                        trackRecordList.add(trackRecord);
                    }
                    if (pd != null) {
                        pd.dismiss();
                    }
                    try {
                        setUpData();
                    } catch (ParseException e) {
                        e.printStackTrace();
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
