package com.ntobeko.confmanagement.ui.conferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConferencesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ConferencesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}