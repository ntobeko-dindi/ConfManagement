package com.ntobeko.confmanagement.ui.addconf;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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

import java.util.Date;
import java.util.Objects;

public class AddConfFragment extends Fragment {

    private FragmentAddconfBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddconfBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.createConference.setOnClickListener(v -> {
            String name, theme, venue, date,abstractSubmissionFee,attendanceFee;

            name = Objects.requireNonNull(binding.conferenceName.getText()).toString();
            theme = Objects.requireNonNull(binding.conferenceTheme.getText()).toString();
            venue = Objects.requireNonNull(binding.conferenceVenue.getText()).toString();
            date = Objects.requireNonNull(binding.conferenceDate.getText()).toString();
            attendanceFee = Objects.requireNonNull(binding.attendanceFee.getText()).toString();
            abstractSubmissionFee = Objects.requireNonNull(binding.abstractSubmissionFee.getText()).toString();

            if(name.equals("") || theme.equals("") || venue.equals("") || date.equals("") || attendanceFee.equals("") || abstractSubmissionFee.equals("")){
                new Utilities().showSnackBar("All fields are required", root);
                return;
            }
            Conference conference = new Conference(name,theme,venue,date, new LocalDate().getLocalDateTime());
            conference.setAbstractSubmissionFee(abstractSubmissionFee);
            conference.setAttendanceFee(attendanceFee);

            new FireBaseHelper().addNewConference(conference, root,getActivity());
        });

        binding.conferenceDate.setOnClickListener(v -> {
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> binding.conferenceDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth), 1900 + new Date().getYear(),new Date().getMonth(),new Date().getDate());
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}