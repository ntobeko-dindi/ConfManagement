package com.ntobeko.confmanagement.ui.authnews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentAuthnewsBinding;

public class AuthNewsFragment extends Fragment {

    private FragmentAuthnewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAuthnewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        new FireBaseHelper().getAuthNews(root, getContext(), binding);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}