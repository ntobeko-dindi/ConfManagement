package com.ntobeko.confmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.button.MaterialButton;
import com.ntobeko.confmanagement.models.LoadingDialog;
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

        approve.setOnClickListener(v -> {
            //approve
            new Utilities().showSnackBar("Approving", viewGroup.getRootView());
        });

        rejectButton.setOnClickListener(v -> {
            //reject
            new Utilities().showSnackBar("Rejecting", viewGroup.getRootView());
        });

        back.setOnClickListener(v -> {
            startActivity(new Intent(PdfViewerActivity.this, AuthActivity.class));
            finish();
        });

        dialog.showLoader();

        new PdfViewerActivity.RetrievePdf().execute("https://www.africau.edu/images/default/sample.pdf");
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