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

import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Commons.Url;
import com.kreators.crtoolv1.Fragment.Listener.SalesOutListener;
import com.kreators.crtoolv1.Network.GetVolleyRequest;
import com.kreators.crtoolv1.Network.VolleyListener;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.Network.VolleyRequest;
import com.kreators.crtoolv1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DELL on 14/06/2016.
 */
public class SalesOutInputFragment extends Fragment {

    private TextView textSN;
    private SalesOutListener activityCallback;
    private VolleyManager volleyManager;
    private ProgressDialog pd;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SalesOutListener) {
            activityCallback = (SalesOutListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SalesOutListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_out_input, container, false);

        textSN = (TextView) view.findViewById(R.id.textSN);

        Bundle bundle = getArguments();

        if(bundle != null) {
            textSN.setText(bundle.getString(Protocol.CONTENT));
        }

        final Button btnSN = (Button) view.findViewById(R.id.btnInputSN);
        btnSN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String input = textSN.getText().toString();
                if(input.length() > 0 && input.matches("[0-9]+")){
                    inputSNButtonClicked(input);
                } else {
                    Toast.makeText(getActivity(), "Wrong input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button scanSN =
                (Button) view.findViewById(R.id.scanSN);
        scanSN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scanButtonClicked();
            }
        });

        volleyManager = VolleyManager.getInstance(getActivity().getApplicationContext());

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait.");
        pd.setTitle("Submitting...");
        pd.setCancelable(false);
        pd.setIndeterminate(true);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        textSN.setText("");
    }

    private void inputSNButtonClicked(String SN) {
        pd.show();
        GetVolleyRequest request = new GetVolleyRequest(Url.SALES_OUT_SN);
        request.putParams(Protocol.CR, "1");
        request.putParams(Protocol.OUTLET, "1");
        request.putParams(Protocol.SN, SN);
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONArray result) {
                try {
                    JSONObject jsonObject = result.getJSONObject(0);
                    String message = jsonObject.getString("message");

                    if (pd != null) {
                        pd.dismiss();
                    }

                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    textSN.setText("");
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
        volleyManager.createRequest(request, "POST");
    }

    private void scanButtonClicked() {
        activityCallback.onScanButtonClick();
    }
}
