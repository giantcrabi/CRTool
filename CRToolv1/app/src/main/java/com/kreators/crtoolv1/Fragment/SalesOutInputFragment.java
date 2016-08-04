package com.kreators.crtoolv1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kreators.crtoolv1.Network.GetVolleyRequest;
import com.kreators.crtoolv1.Network.VolleyManager;
import com.kreators.crtoolv1.Network.VolleyStringListener;
import com.kreators.crtoolv1.R;

/**
 * Created by DELL on 14/06/2016.
 */
public class SalesOutInputFragment extends Fragment {

    private TextView textSN;
    //private SalesOutListener activityCallback;
    private VolleyManager volleyManager;

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
                if(input.length() == 0){
                    Toast.makeText(getActivity(), "Wrong input", Toast.LENGTH_SHORT).show();
                } else {
                    inputSNButtonClicked(input);
                }
            }
        });

        volleyManager = VolleyManager.getInstance(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        textSN.setText("");
    }

    private void inputSNButtonClicked(String SN) {
        //activityCallback.onInputSNButtonClick(SN);
        GetVolleyRequest request = new GetVolleyRequest("http://192.168.1.142/CRTool/test/SN");
        request.putParams("CR", "1");
        request.putParams("outlet", "1");
        request.putParams("SN", SN);

        volleyManager.createPostRequest(request, "POSTSN", new VolleyStringListener() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                textSN.setText("");
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
