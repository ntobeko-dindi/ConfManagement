package com.ntobeko.confmanagement.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentRegisterBinding;
import com.ntobeko.confmanagement.models.AbstractModel;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.Utilities;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

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

        String[] conferences = {"Android",
                                "Flutter",
                                "I tried making it with border like below and i tried making it with border like below.",
                                "React Native"};

        String[] coAuthors = {"Dr B. Mutanga", "Mr P. Dlamini"};

        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, themes);
        ArrayAdapter<String> conferenceAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, conferences);
        ArrayAdapter<String> coAuthorsAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, coAuthors);

        themeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        conferenceAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        coAuthorsAdapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice);

        binding.spinnerThemes.setAdapter(themeAdapter);
        binding.spinnerConference.setAdapter(conferenceAdapter);
        binding.spinnerCoAuthors.setAdapter(coAuthorsAdapter);

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

            new FireBaseHelper().registerForConference(model, root);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}