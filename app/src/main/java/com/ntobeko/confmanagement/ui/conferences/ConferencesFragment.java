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
import com.ntobeko.confmanagement.data.FireBaseHelper;
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

        new FireBaseHelper().getConferences(root, getContext(), binding);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}