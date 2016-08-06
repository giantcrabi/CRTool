package com.kreators.crtoolv1.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    //private SalesOutListener activityCallback;
    private VolleyManager volleyManager;
    private ProgressDialog pd;

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof SalesOutListener) {
//            activityCallback = (SalesOutListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement SalesOutListener");
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_out_input, container, false);

        textSN = (TextView) view.findViewById(R.id.textSN);

        final Button btnSN = (Button) view.findViewById(R.id.btnInputSN);

        Bundle bundle = getArguments();

        if(bundle != null) {
            textSN.setText(bundle.getString("Content"));
        }

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
        //activityCallback.onInputSNButtonClick(SN);
        pd.show();
        GetVolleyRequest request = new GetVolleyRequest("http://192.168.1.142/CRTool/services/SN");
        request.putParams("CR", "1");
        request.putParams("outlet", "1");
        request.putParams("SN", SN);
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

}
