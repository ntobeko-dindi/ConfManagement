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

import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.databinding.FragmentRegisterBinding;
import com.ntobeko.confmanagement.models.Utilities;

import java.lang.reflect.Array;
import java.util.Arrays;

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

        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, themes);
        ArrayAdapter<String> conferenceAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, conferences);

        themeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        conferenceAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        binding.spinnerThemes.setAdapter(themeAdapter);
        binding.spinnerConference.setAdapter(conferenceAdapter);

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}