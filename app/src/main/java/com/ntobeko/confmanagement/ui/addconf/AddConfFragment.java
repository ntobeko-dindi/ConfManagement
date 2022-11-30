package com.ntobeko.confmanagement.ui.addconf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentAddconfBinding;
import com.ntobeko.confmanagement.models.Conference;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.Utilities;

import java.util.Objects;

public class AddConfFragment extends Fragment {

    private FragmentAddconfBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddconfBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.createConference.setOnClickListener(v -> {
            String name, theme, venue, date;
            name = Objects.requireNonNull(binding.conferenceName.getText()).toString();
            theme = Objects.requireNonNull(binding.conferenceTheme.getText()).toString();
            venue = Objects.requireNonNull(binding.conferenceVenue.getText()).toString();
            date = Objects.requireNonNull(binding.conferenceDate.getText()).toString();

            if(name.equals("") || theme.equals("") || venue.equals("") || date.equals("")){
                new Utilities().showSnackBar("All fields are required", root);
                return;
            }
            Conference conference = new Conference(name,theme,venue,date, new LocalDate().getLocalDateTime());
            new FireBaseHelper().addNewConference(conference, root,getActivity());
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}