package com.ntobeko.confmanagement.ui.approvals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.data.ApprovalsListAdapter;
import com.ntobeko.confmanagement.data.NewsListAdapter;
import com.ntobeko.confmanagement.databinding.FragmentApprovalsBinding;
import com.ntobeko.confmanagement.models.AbstractModel;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.NewsArticle;

import java.util.ArrayList;

public class ApprovalsFragment extends Fragment {

    private FragmentApprovalsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentApprovalsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<AbstractModel> abstracts = new ArrayList<>();

        AbstractModel abstractModel = new AbstractModel();
        abstractModel.setTheme("Educational research");
        abstractModel.setAuthors("Authors : Mr Ntobeko Dindi, et al");
        abstractModel.setStatus(ProposalStatus.Submitted);
        abstractModel.setResearchTopic("The effect of online leaning in universities");
        abstractModel.setSubmissionDate("Submitted on : " + new LocalDate().getLocalDateTime());
        abstractModel.setAbstractBody("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

        int rows = 25;
        do {
            abstracts.add(abstractModel);
            rows--;
        } while (rows > 0);

        ListAdapter listAdapter = new ApprovalsListAdapter(getContext(),abstracts);

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