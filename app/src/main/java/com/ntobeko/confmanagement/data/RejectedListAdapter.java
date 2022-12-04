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

import com.ntobeko.confmanagement.PdfViewerActivity;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.models.AbstractModel;

import java.util.ArrayList;

public class RejectedListAdapter extends ArrayAdapter<AbstractModel> {
    private final Activity activity;
    public RejectedListAdapter(Context context, ArrayList<AbstractModel> list, Activity activity){
        super(context, R.layout.approvals_list_view,list);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AbstractModel _abstract = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.approvals_list_view,parent,false);
        }

        TextView theme = convertView.findViewById(R.id.theme);
        TextView status = convertView.findViewById(R.id.status);
        TextView topic = convertView.findViewById(R.id.topic);
        TextView submittedDate = convertView.findViewById(R.id.submissionDate);
        TextView abstractMsg = convertView.findViewById(R.id.abstractBody);
        TextView authors = convertView.findViewById(R.id.authors);
        TextView hiddenId = convertView.findViewById(R.id.hiddenId);
        Button rejectButton = convertView.findViewById(R.id.btnReject);
        Button approveButton = convertView.findViewById(R.id.btnApprove);
        Button pdf = convertView.findViewById(R.id.openAbstractPdf);
        TextView abstractPdfDownloadUrl = convertView.findViewById(R.id.hiddenAbstractPdfDownloadUrl);

        pdf.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), PdfViewerActivity.class);
            i.putExtra("abstractPdfDownloadUrl",abstractPdfDownloadUrl.getText());
            i.putExtra("hiddenConfId",hiddenId.getText());
            i.putExtra("canApprove","0");
            activity.startActivity(i);
        });

        theme.setText(_abstract.getTheme());
        status.setText(_abstract.getStatus().toString());
        topic.setText(_abstract.getResearchTopic());
        submittedDate.setText(_abstract.getSubmissionDate());
        abstractMsg.setText(_abstract.getAbstractBody());
        authors.setText(_abstract.getCoAuthors());
        hiddenId.setText(_abstract.getAbstractId());
        abstractPdfDownloadUrl.setText(_abstract.getAbstractPdfDownloadUrl());

        approveButton.setVisibility(View.GONE);
        rejectButton.setVisibility(View.GONE);

        if(_abstract.getAbstractPdfDownloadUrl().equalsIgnoreCase("")){
            pdf.setVisibility(View.GONE);
        }

        return convertView;
    }
}