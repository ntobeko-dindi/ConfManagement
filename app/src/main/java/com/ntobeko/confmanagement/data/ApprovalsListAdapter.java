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

import com.github.barteksc.pdfviewer.PDFView;
import com.ntobeko.confmanagement.AuthActivity;
import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.Enums.UserRoles;
import com.ntobeko.confmanagement.PdfViewerActivity;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.databinding.FragmentApprovalsBinding;
import com.ntobeko.confmanagement.models.AbstractApproval;
import com.ntobeko.confmanagement.models.AbstractModel;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.Utilities;

import java.util.ArrayList;

public class ApprovalsListAdapter extends ArrayAdapter<AbstractModel> {

    private final FragmentApprovalsBinding fragmentApprovalsBinding;
    private final Activity activity;
    public ApprovalsListAdapter(Context context, ArrayList<AbstractModel> list, FragmentApprovalsBinding fragmentApprovalsBinding, Activity activity){
        super(context, R.layout.approvals_list_view,list);
        this.fragmentApprovalsBinding = fragmentApprovalsBinding;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AbstractModel _abstract = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.approvals_list_view,parent,false);
        }

        String currentUserRole = Utilities.getCurrentUserRoleFromSharedPreferences(getContext());

        TextView theme = convertView.findViewById(R.id.theme);
        TextView status = convertView.findViewById(R.id.status);
        TextView topic = convertView.findViewById(R.id.topic);
        TextView submittedDate = convertView.findViewById(R.id.submissionDate);
        TextView abstractMsg = convertView.findViewById(R.id.abstractBody);
        TextView authors = convertView.findViewById(R.id.authors);
        TextView hiddenConfId = convertView.findViewById(R.id.hiddenId);
        Button approve = convertView.findViewById(R.id.btnApprove);
        Button rejectButton = convertView.findViewById(R.id.btnReject);
        Button pdf = convertView.findViewById(R.id.openAbstractPdf);
        TextView abstractPdfDownloadUrl = convertView.findViewById(R.id.hiddenAbstractPdfDownloadUrl);

        Button openProofOfPayment = convertView.findViewById(R.id.openProofOfPayment);
        TextView downloadProofOfPaymentUrl = convertView.findViewById(R.id.downloadProofOfPaymentUrl);

        String canApprove = "1";

        if(currentUserRole.equalsIgnoreCase(UserRoles.attendee.name())){
            approve.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
            canApprove = "0";
        }

        String finalCanApprove = canApprove;
        pdf.setOnClickListener(v -> {
             Intent i = new Intent(getContext(), PdfViewerActivity.class);
            i.putExtra("abstractPdfDownloadUrl",abstractPdfDownloadUrl.getText());
            i.putExtra("hiddenConfId",hiddenConfId.getText());
            i.putExtra("canApprove", finalCanApprove);
            activity.startActivity(i);
        });


        openProofOfPayment.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), PdfViewerActivity.class);
            i.putExtra("abstractPdfDownloadUrl", downloadProofOfPaymentUrl.getText());
            i.putExtra("hiddenConfId",hiddenConfId.getText());
            i.putExtra("canApprove", finalCanApprove);
            activity.startActivity(i);
        });

        //set the button actions
        View finalConvertView = convertView;
        approve.setOnClickListener(v -> {
            AbstractApproval approveAbstract = new AbstractApproval(hiddenConfId.getText().toString(), ProposalStatus.Approved.name(), new LocalDate().getLocalDateTime());
            String successMsg;
            String failureMsg;

            successMsg = "Conference attendance approved";
            failureMsg = "Error occurred while approving conference attendance";
            new FireBaseHelper().approveAbstract(approveAbstract, finalConvertView, successMsg, failureMsg,getContext(), fragmentApprovalsBinding,activity);

            //Check Dropdown Value if it is Attendance or Abstract Approval
//            if(false) {
//                successMsg = "Conference attendance approved";
//                failureMsg = "Error occurred while approving conference attendance";
//                approveConference.setDecisionStatus(ProposalStatus.Approved.name());
//                new FireBaseHelper().approveAbstract(approveConference, finalConvertView, successMsg, failureMsg);
//            }else {
//                successMsg = "Conference abstract approved";
//                failureMsg = "Error occurred while approving conference abstract";
//                approveConference.setDecisionStatus(ProposalStatus.Rejected.name());
//                new FireBaseHelper().approveAbstract(approveConference, finalConvertView, successMsg, failureMsg);
//            }

        });

        rejectButton.setOnClickListener(v -> {
            AbstractApproval rejectAbstract = new AbstractApproval(hiddenConfId.getText().toString(), ProposalStatus.Rejected.toString(), new LocalDate().getLocalDateTime());

            String successMsg = "Conference abstract rejected";
            String failureMsg = "Error occurred while rejecting conference abstract";
            new FireBaseHelper().approveAbstract(rejectAbstract, finalConvertView, successMsg, failureMsg, getContext(), fragmentApprovalsBinding,activity);

//            if(false) {
//                String successMsg = "Conference attendance rejected";
//                String failureMsg = "Error occurred while rejecting conference attendance";
//                new FireBaseHelper().approveAbstract(rejectConference, finalConvertView, successMsg, failureMsg);
//            }else{
//                String successMsg = "Conference abstract rejected";
//                String failureMsg = "Error occurred while rejecting conference abstract";
//                new FireBaseHelper().approveAbstract(rejectConference, finalConvertView, successMsg, failureMsg);
//            }

        });

        theme.setText(_abstract.getTheme());
        status.setText(_abstract.getStatus().toString());
        topic.setText(_abstract.getResearchTopic());
        submittedDate.setText(_abstract.getSubmissionDate());
        abstractMsg.setText(_abstract.getAbstractBody());
        authors.setText(_abstract.getCoAuthors());
        hiddenConfId.setText(_abstract.getAbstractId());
        abstractPdfDownloadUrl.setText(_abstract.getAbstractPdfDownloadUrl());
        downloadProofOfPaymentUrl.setText(_abstract.getDownloadProofOfPaymentUrl());

        if(_abstract.getAbstractPdfDownloadUrl().equalsIgnoreCase("")){
            pdf.setVisibility(View.GONE);
        }

        return convertView;
    }
}