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
import com.ntobeko.confmanagement.models.Abstract;
import com.ntobeko.confmanagement.models.Credential;

import java.util.ArrayList;

public class ConferencesListAdapter extends ArrayAdapter<Abstract> {

    public ConferencesListAdapter(Context context, ArrayList<Abstract> list){
        super(context, R.layout.news_list_view,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Abstract abs = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.conferences_list_view,parent,false);
        }

        ImageView image = convertView.findViewById(R.id.image);
        TextView name = convertView.findViewById(R.id.title);
        TextView description = convertView.findViewById(R.id.description);
        TextView date = convertView.findViewById(R.id.date);
        TextView offer = convertView.findViewById(R.id.txt_offer);

        name.setText(abs.getName());
        description.setText(abs.getDescription());
        date.setText(abs.getDate());
        offer.setText(abs.getOffer());

        return convertView;
    }
}