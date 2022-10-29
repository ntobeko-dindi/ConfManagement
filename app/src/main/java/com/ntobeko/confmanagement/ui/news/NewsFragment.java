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

import com.ntobeko.confmanagement.data.NewsListAdapter;
import com.ntobeko.confmanagement.databinding.FragmentNewsBinding;
import com.ntobeko.confmanagement.models.Credential;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel dashboardViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textDashboard;
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ArrayList<Credential> credentials = new ArrayList<>();
        Credential obj = new Credential("Facebook","xcoding","pass1234");

        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);
        credentials.add(obj);

        ListAdapter listAdapter = new NewsListAdapter(getContext(),credentials);

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