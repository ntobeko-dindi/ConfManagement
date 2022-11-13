package com.ntobeko.confmanagement.models;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utilities {
    public void showSnackBar(String message, View view){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
