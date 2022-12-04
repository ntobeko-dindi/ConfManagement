package com.ntobeko.confmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.button.MaterialButton;
import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.models.AbstractApproval;
import com.ntobeko.confmanagement.models.LoadingDialog;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.Utilities;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfViewerActivity extends AppCompatActivity {

    private PDFView pdfView;
    LoadingDialog dialog;
    MaterialButton back;
    ViewGroup viewGroup;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        pdfView = findViewById(R.id.pdfView);
        dialog = new LoadingDialog(this);
        back = findViewById(R.id.btnBack);
        Button approve = findViewById(R.id.btnApprove);
        Button rejectButton = findViewById(R.id.btnReject);

        Bundle extras = getIntent().getExtras();
        String hiddenConfId = null;
        String abstractPdfDownloadUrl = null;

        if (extras != null) {
            abstractPdfDownloadUrl = extras.getString("abstractPdfDownloadUrl");
            hiddenConfId = extras.getString("hiddenConfId");
            new PdfViewerActivity.RetrievePdf().execute(abstractPdfDownloadUrl);
        }

        String finalHiddenConfId = hiddenConfId;
        approve.setOnClickListener(v -> {
            AbstractApproval approveAbstract = new AbstractApproval(finalHiddenConfId, ProposalStatus.Approved.name(), new LocalDate().getLocalDateTime());
            String successMsg;
            String failureMsg;

            successMsg = "Conference attendance approved";
            failureMsg = "Error occurred while approving conference attendance";
            new FireBaseHelper().approvePdfAbstract(approveAbstract, viewGroup.getRootView(), successMsg, failureMsg,viewGroup.getContext(),this);
        });

        rejectButton.setOnClickListener(v -> {
            AbstractApproval approveAbstract = new AbstractApproval(finalHiddenConfId, ProposalStatus.Rejected.name(), new LocalDate().getLocalDateTime());
            String successMsg;
            String failureMsg;

            successMsg = "Conference attendance rejected";
            failureMsg = "Error occurred while rejecting conference attendance";
            new FireBaseHelper().approvePdfAbstract(approveAbstract, viewGroup.getRootView(), successMsg, failureMsg,viewGroup.getContext(),this);
        });

        back.setOnClickListener(v -> {
            startActivity(new Intent(PdfViewerActivity.this, AuthActivity.class));
            finish();
        });

        if (abstractPdfDownloadUrl != null) {
            dialog.showLoader();
            new PdfViewerActivity.RetrievePdf().execute(abstractPdfDownloadUrl);
        }


    }
    class RetrievePdf extends AsyncTask<String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try{
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if(urlConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            }catch (Exception e){
                dialog.dismissLoader();
                new Utilities().showSnackBar(e.getMessage(), new View(getApplicationContext()));
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).onLoad(nbPages -> dialog.dismissLoader()).load();
        }
    }
}