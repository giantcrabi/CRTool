package com.kreators.crtoolv1.Fragment.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kreators.crtoolv1.Commons.Protocol;
import com.kreators.crtoolv1.Model.IndoCurrencyFormat;
import com.kreators.crtoolv1.Model.TrackRecord;
import com.kreators.crtoolv1.R;

/**
 * Created by Julio Anthony Leonar on 8/5/2016.
 */
public class TrackRecordDialogFragment extends DialogFragment {

    private TrackRecord mTrackRecord,trackRecord;
    private TextView jumlah,approve,received,submitted,retur,target,kurang,presentasi;
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
        jumlah.setText("Appr: " + IndoCurrencyFormat.transformIntegerToRupiah(trackRecord.getPrice()));
        target.setText("Target: "+ IndoCurrencyFormat.transformIntegerToRupiah(300000000));
        kurang.setText("Kurang: " + IndoCurrencyFormat.transformIntegerToRupiah(300000000 - trackRecord.getPrice()));
        presentasi.setText("Presentasi: " + String.format("%.2f",((double)trackRecord.getPrice()/3000000))+"%");
        submitted.setText("Submitted: "+ String.valueOf(trackRecord.getSubmitted()));
        received.setText("Received: " + String.valueOf(trackRecord.getReceived()));
        approve.setText("Approved: " + String.valueOf(trackRecord.getApproved()));
        retur.setText("Retur: " + String.valueOf(trackRecord.getRetur()));
        return rootView;
    }
    private void bind() {
        jumlah = (TextView) rootView.findViewById(R.id.tvJumlahTrackRecordDetail);
        target = (TextView) rootView.findViewById(R.id.tvTargetTrackRecordDetail);
        kurang = (TextView) rootView.findViewById(R.id.tvKurangTrackrecordDetail);
        presentasi = (TextView) rootView.findViewById(R.id.tvPresentasiTrackRecordDetail);
        submitted = (TextView) rootView.findViewById(R.id.tvSubmittedTrackRecordDetail);
        received = (TextView) rootView.findViewById(R.id.tvReceivedTrackRecordDetail);
        approve = (TextView) rootView.findViewById(R.id.tvApprovedTrackRecordDetail);
        retur = (TextView) rootView.findViewById(R.id.tvReturnedTrackRecordDetail);
    }

}
