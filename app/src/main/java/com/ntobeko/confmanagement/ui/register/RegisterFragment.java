package com.ntobeko.confmanagement.ui.register;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentRegisterBinding;
import com.ntobeko.confmanagement.models.AbstractModel;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.Utilities;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private final int CHOOSE_PDF_FROM_DEVICE = 1001;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegisterViewModel galleryViewModel =
                new ViewModelProvider(this).get(RegisterViewModel.class);

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String[] themes = {"Focus on the Future.",
                            "Above and Beyond.",
                            "Discover the Difference.",
                            "Stop Selling, Start Closing.",
                            "Finding Your Mojo.",
                            "Reinvigorating Your Sales Mojo",
                            "Ain't No Stopping Us Now.",
                            "Living The Sales Life."};

        String[] coAuthors = {"Dr B. Mutanga", "Mr P. Dlamini"};

        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, themes);
        new FireBaseHelper().populateConferenceDropdown(root, getContext(),binding,getActivity());
        new FireBaseHelper().populateCoAuthorsDropdown(root, getContext(),binding, getActivity());

        themeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        binding.spinnerThemes.setAdapter(themeAdapter);

        binding.register.setOnClickListener(v -> {

            String conference = binding.spinnerConference.getText().toString();
            String title = Objects.requireNonNull(binding.researchTopic.getText()).toString();
            String theme = binding.spinnerThemes.getText().toString();
            String body = Objects.requireNonNull(binding.abstractBody.getText()).toString();
            String _coAuthors = binding.spinnerCoAuthors.getText().toString();

            if(conference.equals("")){
                new Utilities().showSnackBar("Please select conference", root);
                return;
            }
            if(theme.equals("")){
                new Utilities().showSnackBar("Please select theme", root);
                return;
            }
            if(title.equals("") || body.equals("")){
                new Utilities().showSnackBar("Both research topic and abstract are required", root);
                return;
            }
            AbstractModel model = new AbstractModel();
            model.setResearchTopic(title);
            model.setAbstractBody(body);
            model.setStatus(ProposalStatus.Submitted);
            model.setConferenceId(conference);
            model.setSubmissionDate(new LocalDate().getLocalDateTime());
            model.setCoAuthors(_coAuthors);
            model.setTheme(theme);

            new FireBaseHelper().submitConferenceAbstract(model, root,getActivity());
        });

        binding.chooseFile.setOnClickListener(v -> chooseFileFromDevice());

        return root;
    }

    private void chooseFileFromDevice(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent, CHOOSE_PDF_FROM_DEVICE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_PDF_FROM_DEVICE && resultCode == RESULT_OK){
            new Utilities().showSnackBar("Path is :=> " + data.getData(),getView());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}