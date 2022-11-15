package com.ntobeko.confmanagement.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.models.AbstractModel;
import com.ntobeko.confmanagement.models.NewsArticle;

import java.util.ArrayList;

public class ApprovalsListAdapter extends ArrayAdapter<AbstractModel> {

    public ApprovalsListAdapter(Context context, ArrayList<AbstractModel> list){
        super(context, R.layout.news_list_view,list);
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

        theme.setText(_abstract.getTheme());
        status.setText(_abstract.getStatus().toString());
        topic.setText(_abstract.getResearchTopic());
        submittedDate.setText(_abstract.getSubmissionDate());
        abstractMsg.setText(_abstract.getAbstractBody());
        authors.setText(_abstract.getCoAuthors());

        return convertView;
    }
}