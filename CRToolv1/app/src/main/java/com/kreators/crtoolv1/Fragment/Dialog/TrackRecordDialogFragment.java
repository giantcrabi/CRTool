package com.kreators.crtoolv1.Fragment.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Model.TrackRecord;
import com.kreators.crtoolv1.R;

/**
 * Created by Julio Anthony Leonar on 8/5/2016.
 */
public class TrackRecordDialogFragment extends DialogFragment {

    private TrackRecord mTrackRecord,trackRecord;
    private TextView appr,target,kurang,presentasi;
    private View rootView;


    public static TrackRecordDialogFragment newInstance(TrackRecord trackRecordDetails) {
        TrackRecordDialogFragment f = new TrackRecordDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(Protocol.TRACK_RECORD_DETAILS, trackRecordDetails);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTrackRecord = (TrackRecord) getArguments().getSerializable(Protocol.TRACK_RECORD_DETAILS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_track_record_detail_dialog, container, false);
        bind();
        trackRecord = mTrackRecord;
        getDialog().setTitle(trackRecord.getBulan());
        appr.setText("Appr: " + String.valueOf(trackRecord.getPrice()));
        target.setText("Target: 3000000");
        kurang.setText("Kurang: " + String.valueOf(3000000 - trackRecord.getPrice()));
        presentasi.setText("Presentasi: " + String.valueOf(trackRecord.getPrice()/30000)+"%");
        return rootView;
    }
    private void bind() {
        appr = (TextView) rootView.findViewById(R.id.tvApprTrackRecordDetail);
        target = (TextView) rootView.findViewById(R.id.tvTargetTrackRecordDetail);
        kurang = (TextView) rootView.findViewById(R.id.tvKurangTrackrecordDetail);
        presentasi =(TextView) rootView.findViewById(R.id.tvPresentasiTrackRecordDetail);
    }

}
