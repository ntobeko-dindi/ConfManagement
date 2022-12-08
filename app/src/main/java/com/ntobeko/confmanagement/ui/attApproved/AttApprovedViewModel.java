package com.ntobeko.confmanagement.ui.attApproved;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AttApprovedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AttApprovedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}