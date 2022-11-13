package com.ntobeko.confmanagement.ui.approvals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ntobeko.confmanagement.databinding.FragmentApprovalsBinding;

public class ApprovalsFragment extends Fragment {

    private FragmentApprovalsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ApprovalsViewModel homeViewModel =
                new ViewModelProvider(this).get(ApprovalsViewModel.class);

        binding = FragmentApprovalsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}