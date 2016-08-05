package com.kreators.crtoolv1.Fragment.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreators.crtoolv1.Model.SerialNumber;
import com.kreators.crtoolv1.R;

/**
 * Created by Julio Anthony Leonar on 8/5/2016.
 */
public class TrackRecordDialogFragment extends DialogFragment {

    SerialNumber mDate,date;

    public static TrackRecordDialogFragment newInstance(SerialNumber trackRecordDetails) {
        TrackRecordDialogFragment f = new TrackRecordDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("detailTrackRecord", trackRecordDetails);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = (SerialNumber) getArguments().getSerializable("detailTrackRecord");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_record_detail_dialog, container, false);
        date = mDate;
        getDialog().setTitle(date.getDate());
        return rootView;
    }

}
