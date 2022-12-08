package com.ntobeko.confmanagement.ui.attRejected;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentAttrejectedBinding;

public class AttRejectedFragment extends Fragment {

    private FragmentAttrejectedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAttrejectedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        new FireBaseHelper().getConferenceRejectedAttendance(root,getContext(),binding, getActivity());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}