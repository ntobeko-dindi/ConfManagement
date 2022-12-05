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

import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.Enums.UserRoles;
import com.ntobeko.confmanagement.PdfViewerActivity;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.databinding.FragmentApprovalsBinding;
import com.ntobeko.confmanagement.databinding.FragmentAttapprovalsBinding;
import com.ntobeko.confmanagement.models.AbstractApproval;
import com.ntobeko.confmanagement.models.AbstractModel;
import com.ntobeko.confmanagement.models.ConferenceAttendance;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.SubmitConferenceAttendance;
import com.ntobeko.confmanagement.models.Utilities;

import java.util.ArrayList;

public class AttApprovalsListAdapter extends ArrayAdapter<SubmitConferenceAttendance> {

    private final FragmentAttapprovalsBinding fragmentApprovalsBinding;
    private final Activity activity;
    public AttApprovalsListAdapter(Context context, ArrayList<SubmitConferenceAttendance> list, FragmentAttapprovalsBinding fragmentAttapprovalsBinding, Activity activity){
        super(context, R.layout.att_approvals_list_view,list);
        this.fragmentApprovalsBinding = fragmentAttapprovalsBinding;
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
        TextView attendType = convertView.findViewById(R.id.attendanceType);
        TextView regDate = convertView.findViewById(R.id.registrationDate);
        TextView attendStatus = convertView.findViewById(R.id.status);
        TextView hiddenAttId = convertView.findViewById(R.id.hiddenAttId);

//        TextView theme = convertView.findViewById(R.id.theme);
//        TextView status = convertView.findViewById(R.id.status);
//        TextView topic = convertView.findViewById(R.id.topic);
//        TextView submittedDate = convertView.findViewById(R.id.submissionDate);
//        TextView abstractMsg = convertView.findViewById(R.id.abstractBody);
//        TextView authors = convertView.findViewById(R.id.authors);
//        TextView hiddenConfId = convertView.findViewById(R.id.hiddenId);

        Button approve = convertView.findViewById(R.id.btnApprove);
        Button rejectButton = convertView.findViewById(R.id.btnReject);

//        Button pdf = convertView.findViewById(R.id.openAbstractPdf);
//        TextView abstractPdfDownloadUrl = convertView.findViewById(R.id.hiddenAbstractPdfDownloadUrl);

        Button openProofOfPayment = convertView.findViewById(R.id.openProofOfPayment);
        TextView downloadProofOfPaymentUrl = convertView.findViewById(R.id.downloadProofOfPaymentUrl);

        String canApprove = "1";

        if(currentUserRole.equalsIgnoreCase(UserRoles.attendee.name())){
            approve.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
            canApprove = "0";
        }

        String finalCanApprove = canApprove;
//        pdf.setOnClickListener(v -> {
//             Intent i = new Intent(getContext(), PdfViewerActivity.class);
//            i.putExtra("abstractPdfDownloadUrl",abstractPdfDownloadUrl.getText());
//            i.putExtra("hiddenConfId",hiddenConfId.getText());
//            i.putExtra("canApprove", finalCanApprove);
//            activity.startActivity(i);
//        });


        openProofOfPayment.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), PdfViewerActivity.class);
            i.putExtra("abstractPdfDownloadUrl", downloadProofOfPaymentUrl.getText());
            i.putExtra("hiddenConfId",hiddenAttId.getText());
            i.putExtra("canApprove", finalCanApprove);
            activity.startActivity(i);
        });

        //set the button actions
        View finalConvertView = convertView;
        approve.setOnClickListener(v -> {
//            ConferenceAttendance approveAbstract = new AbstractApproval(hiddenConfId.getText().toString(), ProposalStatus.Approved.name(), new LocalDate().getLocalDateTime());
//            String successMsg;
//            String failureMsg;
//
//            successMsg = "Conference attendance approved";
//            failureMsg = "Error occurred while approving conference attendance";
//            new FireBaseHelper().approveConferenceAttendance(approveAbstract, finalConvertView, successMsg, failureMsg,getContext(), fragmentApprovalsBinding,activity);

        });

        rejectButton.setOnClickListener(v -> {
//            AbstractApproval rejectAbstract = new AbstractApproval(hiddenConfId.getText().toString(), ProposalStatus.Rejected.toString(), new LocalDate().getLocalDateTime());
//
//            String successMsg = "Conference abstract rejected";
//            String failureMsg = "Error occurred while rejecting conference abstract";
//            new FireBaseHelper().approveAbstract(rejectAbstract, finalConvertView, successMsg, failureMsg, getContext(), fragmentApprovalsBinding,activity);

        });

        confName.setText(_confAttendance.getConferenceId());
        attendType.setText(_confAttendance.getRegistrationType());
        regDate.setText(_confAttendance.getRegistrationDate());
        attendStatus.setText(_confAttendance.getStatus().name());
        hiddenAttId.setText(_confAttendance.getConferenceId());
        downloadProofOfPaymentUrl.setText(_confAttendance.getDownloadProofOfPaymentUrl());

//        theme.setText("");
//        status.setText(_abstract.getStatus().toString());
//        //topic.setText(_abstract.getAttendanceId());
//        submittedDate.setText(_abstract.getRegistrationDate());
//        abstractMsg.setText("");
//        topic.setText(_abstract.getRegistrationType());
//        hiddenConfId.setText(_abstract.getConferenceId());
//        abstractPdfDownloadUrl.setText("");
//        downloadProofOfPaymentUrl.setText(_abstract.getDownloadProofOfPaymentUrl());

        return convertView;
    }
}