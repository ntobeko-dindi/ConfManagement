package com.ntobeko.confmanagement.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ntobeko.confmanagement.Enums.UserRoles;
import com.ntobeko.confmanagement.PdfViewerActivity;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.models.SubmitConferenceAttendance;
import com.ntobeko.confmanagement.models.Utilities;

import java.util.ArrayList;

public class AttApprovedListAdapter extends ArrayAdapter<SubmitConferenceAttendance> {

    private final Activity activity;
    public AttApprovedListAdapter(Context context, ArrayList<SubmitConferenceAttendance> list, Activity activity){
        super(context, R.layout.att_approvals_list_view,list);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SubmitConferenceAttendance _confAttendance = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.att_approvals_list_view,parent,false);
        }

        String currentUserRole = Utilities.getCurrentUserRoleFromSharedPreferences(getContext());

        TextView confName = convertView.findViewById(R.id.conferenceName);
        TextView attendType = convertView.findViewById(R.id.attendType);
        TextView regDate = convertView.findViewById(R.id.registrationDate);
        TextView attendStatus = convertView.findViewById(R.id.status);
        TextView hiddenAttId = convertView.findViewById(R.id.hiddenAttId);

        Button approve = convertView.findViewById(R.id.btnApprove);
        Button rejectButton = convertView.findViewById(R.id.btnReject);

        Button openProofOfPayment = convertView.findViewById(R.id.openProofOfPayment);
        TextView downloadProofOfPaymentUrl = convertView.findViewById(R.id.downloadProofOfPaymentUrl);

        String canApprove = "1";

        if(currentUserRole.equalsIgnoreCase(UserRoles.attendee.name())){
            approve.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
            canApprove = "0";
        }

        String finalCanApprove = canApprove;

        openProofOfPayment.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), PdfViewerActivity.class);
            i.putExtra("abstractPdfDownloadUrl", downloadProofOfPaymentUrl.getText());
            i.putExtra("hiddenConfId",hiddenAttId.getText());
            i.putExtra("canApprove", finalCanApprove);
            activity.startActivity(i);
        });

        confName.setText(_confAttendance.getConferenceName());
        attendType.setText(_confAttendance.getRegistrationType());
        regDate.setText(_confAttendance.getRegistrationDate());
        attendStatus.setText(_confAttendance.getStatus().name());
        hiddenAttId.setText(_confAttendance.getAttendanceId());
        downloadProofOfPaymentUrl.setText(_confAttendance.getDownloadProofOfPaymentUrl());

        return convertView;
    }
}