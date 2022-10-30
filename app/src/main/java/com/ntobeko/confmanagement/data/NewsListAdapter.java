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
import com.ntobeko.confmanagement.models.NewsArticle;

import java.util.ArrayList;

public class NewsListAdapter extends ArrayAdapter<NewsArticle> {

    public NewsListAdapter(Context context, ArrayList<NewsArticle> list){
        super(context, R.layout.news_list_view,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NewsArticle article = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_view,parent,false);
        }

        TextView title = convertView.findViewById(R.id.txt_title);
        TextView msg = convertView.findViewById(R.id.description);

        title.setText(article.getTitle());
        msg.setText(article.getDescription());

        return convertView;
    }
}