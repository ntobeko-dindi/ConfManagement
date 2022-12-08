package com.ntobeko.confmanagement.ui.register;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.data.FireBaseStorageHelper;
import com.ntobeko.confmanagement.databinding.FragmentRegisterBinding;
import com.ntobeko.confmanagement.models.AbstractModel;
import com.ntobeko.confmanagement.models.ConferenceAttendance;
import com.ntobeko.confmanagement.models.LoadingDialog;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.SubmitConferenceAttendance;
import com.ntobeko.confmanagement.models.Utilities;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private final int CHOOSE_PDF_FROM_DEVICE = 1001;
    private StorageReference mStorageRef;
    String docName = null;

    String selectedConfId ;

    String selectedRegType = "";
    String folderName = "";
    Boolean isAbstractSubmission = false;

    @SuppressLint("NonConstantResourceId")
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

        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, themes);
        new FireBaseHelper().populateConferenceDropdown(root, getContext(),binding,getActivity());
        new FireBaseHelper().populateCoAuthorsDropdown(root, getContext(),binding, getActivity());

        mStorageRef = FirebaseStorage.getInstance().getReference();

        themeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        binding.spinnerThemes.setAdapter(themeAdapter);

        RadioGroup btnGroupRegType = root.findViewById(R.id.rgRegType);
        btnGroupRegType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbAttend:
                    selectedRegType = "Attendee";
                    root.findViewById(R.id.rgIsAbstractSubmission).setVisibility(View.GONE);
                    root.findViewById(R.id.txtIsAbstractSubmission).setVisibility(View.GONE);
                    root.findViewById(R.id.layoutTitle).setVisibility(View.GONE);
                    root.findViewById(R.id.TextInputLayout).setVisibility(View.GONE);
                    root.findViewById(R.id.layoutCoAuthors).setVisibility(View.GONE);
                    root.findViewById(R.id.layoutAbstractBody).setVisibility(View.GONE);
                    root.findViewById(R.id.chooseFile).setVisibility(View.GONE);
                    break;
                case R.id.rbAuthor:
                    selectedRegType = "Author";
                    root.findViewById(R.id.txtIsAbstractSubmission).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.rgIsAbstractSubmission).setVisibility(View.VISIBLE);
                    break;
                case R.id.rbCoAuthor:
                    selectedRegType = "CoAuthor";
                    root.findViewById(R.id.txtIsAbstractSubmission).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.rgIsAbstractSubmission).setVisibility(View.VISIBLE);
                    break;
            }
        });

        RadioGroup rgIsAbstractSubmission = root.findViewById(R.id.rgIsAbstractSubmission);
        rgIsAbstractSubmission.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbIsAbstractYes:
                    isAbstractSubmission = true;
                    root.findViewById(R.id.layoutTitle).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.TextInputLayout).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.layoutCoAuthors).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.layoutAbstractBody).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.chooseFile).setVisibility(View.VISIBLE);
                    break;
                case R.id.rbIsAbstractNo:
                    isAbstractSubmission = false;
                    root.findViewById(R.id.layoutTitle).setVisibility(View.GONE);
                    root.findViewById(R.id.TextInputLayout).setVisibility(View.GONE);
                    root.findViewById(R.id.layoutCoAuthors).setVisibility(View.GONE);
                    root.findViewById(R.id.layoutAbstractBody).setVisibility(View.GONE);
                    root.findViewById(R.id.chooseFile).setVisibility(View.GONE);
                    break;
            }
        });

        binding.spinnerConference.setOnItemClickListener((parent, view, position, id) -> {
            String arr [] = binding.hiddenConfIds.getText().toString().split("\\|");
            selectedConfId = arr[position];
            new Utilities().showSnackBar(selectedConfId , root);
        });

        binding.register.setOnClickListener(v -> {
            String conference = binding.spinnerConference.getText().toString();
            String title = Objects.requireNonNull(binding.researchTopic.getText()).toString();
            String theme = binding.spinnerThemes.getText().toString();
            String body = Objects.requireNonNull(binding.abstractBody.getText()).toString();
            String _coAuthors = binding.spinnerCoAuthors.getText().toString();


            if(conference.equals("selectedRegType")){
                new Utilities().showSnackBar("Please select conference", root);
                return;
            }

            SubmitConferenceAttendance confAttendance = new SubmitConferenceAttendance(selectedConfId, selectedRegType.equals("") ? "Attendee" : selectedRegType, isAbstractSubmission);
            confAttendance.setDownloadProofOfPaymentUrl(binding.downloadProofOfPaymentUrl.getText().toString());
            confAttendance.setConferenceName(conference);
            new FireBaseHelper().submitConferenceAttendance(confAttendance, root,getActivity());

            if((selectedRegType.equals("Author") || selectedRegType.equals("CoAuthor") && isAbstractSubmission)) {
                if(theme.equals("")){
                    new Utilities().showSnackBar("Please select theme", root);
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
                model.setAbstractPdfDownloadUrl(binding.hiddenConfIds.getText().toString());
                model.setDownloadProofOfPaymentUrl(binding.downloadProofOfPaymentUrl.getText().toString());

                if(!binding.hiddenConfIds.getText().toString().substring(0,5).equalsIgnoreCase("https")){
                    model.setAbstractPdfDownloadUrl("");
                }

                new FireBaseHelper().submitConferenceAbstract(model, root,getActivity());
            }
        });

        binding.chooseFile.setOnClickListener(v -> {
            if(binding.spinnerConference.getText().toString().equals("")){
                new Utilities().showSnackBar("Please Select A Conference", root);
                return;
            }
            docName = "abstract";
            folderName = "Abstracts";
            chooseFileFromDevice();
        });

        binding.uploadProofOfPayment.setOnClickListener(v -> {
            if(binding.spinnerConference.getText().toString().equals("")){
                new Utilities().showSnackBar("Please Select A Conference", root);
                return;
            }
            docName = "payment";
            folderName = "Payments";
            chooseFileFromDevice();
        });
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

        String storageRefPath = folderName + "/" + selectedConfId + "/" + Objects.requireNonNull(new FireBaseHelper().getmAuth().getCurrentUser()).getUid() + "/" + docName;

        if(requestCode == CHOOSE_PDF_FROM_DEVICE && resultCode == RESULT_OK){
            assert data != null;
            new FireBaseStorageHelper().uploadImage(data.getData(),getActivity(),storageRefPath,getView(), docName);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}