package com.kreators.crtoolv1.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private ProfileMainListener activityCallback;
    private View view;
    private TextView tvCRName, tvCREmail, tvCRPhone, tvBankName, tvBankAccountName, tvBankAccountNumber;
    private Button btnEditProfile, btnChangePwd;
    private SessionManager session;
    private VolleyManager volleyManager;
    private ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        initialization();
        fetchData();
        return view;
    }

    private void initialization() {
        getActivity().setTitle(Constant.fragmentTitleProfile);
        tvCRName = (TextView) view.findViewById(R.id.tvCRName);
        tvCREmail= (TextView) view.findViewById(R.id.tvCREmail);
        tvCRPhone = (TextView) view.findViewById(R.id.tvCRPhone);
        tvBankName = (TextView) view.findViewById(R.id.tvCRBankName);
        tvBankAccountName   = (TextView) view.findViewById(R.id.tvCRBankAccountName);
        tvBankAccountNumber = (TextView) view.findViewById(R.id.tvBankAccountNumber);
        btnEditProfile = (Button) view.findViewById(R.id.fragment_profile_button_edit);
        btnChangePwd = (Button) view.findViewById(R.id.fragment_profile_button_change_password);
        volleyManager = VolleyManager.getInstance(getActivity().getApplicationContext());
        session = new SessionManager(getActivity().getApplicationContext());
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
    }

    private void fetchData(){
        pd.setTitle(Constant.salesOutDialog);
        pd.show();
        GetVolleyRequest request = new GetVolleyRequest(Url.PROFILE);
        request.putParams(Protocol.CRID, String.valueOf(session.getUserDetails().get(Protocol.USERID)));
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                try {
                    JSONObject response;
                    response = result.getJSONObject(0);
                    tvCRName.setText(tvCRName.getText() + ": " + response.getString(Protocol.CRName));
                    tvCREmail.setText(tvCREmail.getText() + ": " +response.getString(Protocol.CREmail));
                    tvCRPhone.setText(tvCRPhone.getText() + ": " +response.getString(Protocol.CRHP));
                    tvBankAccountName.setText(tvBankAccountName.getText() + ": " +response.getString(Protocol.CRBankAccountName));
                    tvBankName.setText(tvBankName.getText() + ": " +response.getString(Protocol.CRBankName));
                    tvBankAccountNumber.setText(tvBankAccountNumber.getText() + ": " +response.getString(Protocol.CRBankAccountNo));

                    if (pd != null) {
                        pd.dismiss();
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
        volleyManager.createRequest(request, Protocol.GET);
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




}
