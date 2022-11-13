package com.ntobeko.confmanagement.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.type.DateTime;
import com.ntobeko.confmanagement.data.NewsListAdapter;
import com.ntobeko.confmanagement.databinding.FragmentNewsBinding;
import com.ntobeko.confmanagement.models.Credential;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.NewsArticle;

import java.sql.Date;
import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel dashboardViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<NewsArticle> articles = new ArrayList<>();

        NewsArticle article = new NewsArticle("Ntobeko is presenting in room A56",
                                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
                                        "userId",
                                        new LocalDate().getLocalDateTime(),
                                        "");

        int rows = 25;
        do {
            articles.add(article);
            rows--;
        } while (rows > 0);

        ListAdapter listAdapter = new NewsListAdapter(getContext(),articles);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}