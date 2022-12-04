package com.ntobeko.confmanagement.data;

import android.annotation.SuppressLint;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class NewsListAdapter extends ArrayAdapter<NewsArticle> {

    public NewsListAdapter(Context context, ArrayList<NewsArticle> list){
        super(context, R.layout.news_list_view,list);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NewsArticle article = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_view,parent,false);
        }

        TextView title = convertView.findViewById(R.id.title);
        TextView date = convertView.findViewById(R.id.date);
        TextView msg = convertView.findViewById(R.id.description);

        title.setText(article.getTitle());
        msg.setText(article.getDescription());


        String lapsedTime = getLapsedTimeBetweenDates(article.getDatePosted());
        date.setText(lapsedTime);

        return convertView;
    }

    public String getLapsedTimeBetweenDates(String date){
        String dateAfterString = new com.ntobeko.confmanagement.models.LocalDate().getLocalDateTime();

        //Parsing the date
        LocalDate dateBefore = LocalDate.parse(date.substring(0,10));
        LocalDate dateAfter = LocalDate.parse(dateAfterString.substring(0,10));

        //calculating number of days in between
        long days = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        if(days > 0)
            return "Posted " + days + " days ago";

        LocalTime timeBefore = LocalTime.parse(date.substring(11));
        LocalTime timeAfter = LocalTime.parse(dateAfterString.substring(11));

        //calculating number of hours in between
        long hours = ChronoUnit.HOURS.between(timeBefore, timeAfter);
        if(hours > 0)
            return "Posted " + hours + " hours ago";

        //calculating number of seconds in between
        long minutes = ChronoUnit.MINUTES.between(timeBefore, timeAfter);

        if(minutes > 0)
            return "Posted " + minutes + " minutes ago";

        return "Posted " +  ChronoUnit.SECONDS.between(timeBefore, timeAfter) + " seconds ago";
    }
}