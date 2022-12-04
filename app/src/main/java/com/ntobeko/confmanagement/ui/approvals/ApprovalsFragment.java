package com.ntobeko.confmanagement.ui.approvals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentApprovalsBinding;

public class ApprovalsFragment extends Fragment {

    private FragmentApprovalsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentApprovalsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        new FireBaseHelper().getAbstractsPendingApprovals(root,getContext(),binding, getActivity(), ProposalStatus.Submitted,false);
        //new FireBaseHelper().getConferenceAttendeePendingApprovals(root,getContext(),binding);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}