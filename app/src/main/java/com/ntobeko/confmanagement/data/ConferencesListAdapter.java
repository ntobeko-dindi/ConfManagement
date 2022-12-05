package com.ntobeko.confmanagement.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.models.Conference;
import java.util.ArrayList;

public class ConferencesListAdapter extends ArrayAdapter<Conference> {

    public ConferencesListAdapter(Context context, ArrayList<Conference> list){
        super(context, R.layout.conferences_list_view,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Conference conference = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.conferences_list_view,parent,false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView theme = convertView.findViewById(R.id.theme);
        TextView venue = convertView.findViewById(R.id.venue);
        TextView date = convertView.findViewById(R.id.date);
        TextView createdDate = convertView.findViewById(R.id.createdDate);
        TextView attendanceFee = convertView.findViewById(R.id.attendanceFee);
        TextView abstractSubmissionFee = convertView.findViewById(R.id.abstractSubmissionFee);

        name.setText(conference.getName());
        theme.setText(conference.getTheme());
        venue.setText(conference.getVenue());
        date.setText(conference.getDate());
        createdDate.setText(conference.getCreatedDate());
        attendanceFee.setText(conference.getAttendanceFee());
        abstractSubmissionFee.setText(conference.getAbstractSubmissionFee());

        return convertView;
    }
}