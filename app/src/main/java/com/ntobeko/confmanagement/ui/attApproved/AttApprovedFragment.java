package com.ntobeko.confmanagement.ui.attApproved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentAttapprovedBinding;

public class AttApprovedFragment extends Fragment {

    private FragmentAttapprovedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAttapprovedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        new FireBaseHelper().getConferenceApprovedAttendance(root,getContext(),binding, getActivity());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}