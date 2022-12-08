package com.ntobeko.confmanagement.ui.attApprovals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentAttapprovalsBinding;
import com.ntobeko.confmanagement.models.Utilities;

import java.util.Objects;

public class AttApprovalsFragment extends Fragment {

    private FragmentAttapprovalsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAttapprovalsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String currentUserRole = Utilities.getCurrentUserRoleFromSharedPreferences(getContext());

        new FireBaseHelper().getConferenceAttendeePendingApprovals(root,getContext(),binding, getActivity());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}