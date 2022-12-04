package com.ntobeko.confmanagement.ui.approved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.Enums.UserRoles;
import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentApprovedBinding;
import com.ntobeko.confmanagement.models.Utilities;

public class ApprovedFragment extends Fragment {

    private FragmentApprovedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentApprovedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String currentUserRole = Utilities.getCurrentUserRoleFromSharedPreferences(getContext());

        new FireBaseHelper().getAbstractsPendingApprovals(root,getContext(),binding, getActivity(), ProposalStatus.Approved, currentUserRole.equalsIgnoreCase(UserRoles.attendee.name()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}