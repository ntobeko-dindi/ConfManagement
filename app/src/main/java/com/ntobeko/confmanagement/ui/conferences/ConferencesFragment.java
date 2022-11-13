package com.ntobeko.confmanagement.ui.conferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ntobeko.confmanagement.data.ConferencesListAdapter;
import com.ntobeko.confmanagement.databinding.FragmentConferencesBinding;
import com.ntobeko.confmanagement.models.Abstract;

import java.util.ArrayList;

public class ConferencesFragment extends Fragment {

    private FragmentConferencesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConferencesViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ConferencesViewModel.class);

        binding = FragmentConferencesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Abstract> abstracts = new ArrayList<>();
        Abstract obj = new Abstract("https://image.com","Ntobeko Dindi", "As this is just a custom ImageView and not a custom Drawable or a combination of both, it can be used with all kinds of drawables","29 Oct 2022","Live Presentation");

        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);
        abstracts.add(obj);

        ListAdapter listAdapter = new ConferencesListAdapter(getContext(),abstracts);

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