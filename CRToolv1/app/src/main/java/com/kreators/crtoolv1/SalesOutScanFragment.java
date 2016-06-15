package com.kreators.crtoolv1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by DELL on 15/06/2016.
 */
public class SalesOutScanFragment extends Fragment {

    private ImageView mImageview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_out_scan, container, false);

        mImageview = (ImageView) view.findViewById(R.id.imageSN);

        Bitmap imageBitmap = (Bitmap) getArguments().get("data");
        mImageview.setImageBitmap(imageBitmap);

        return view;
    }
}
