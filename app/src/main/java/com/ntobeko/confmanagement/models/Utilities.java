package com.ntobeko.confmanagement.models;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public void showSnackBar(String message, View view){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    public static<T> List<T> reverseList(List<T> list) {
        return new ArrayList<>(Lists.reverse(list));
    }
}
