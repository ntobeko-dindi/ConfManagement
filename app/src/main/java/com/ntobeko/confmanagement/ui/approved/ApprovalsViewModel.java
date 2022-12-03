package com.ntobeko.confmanagement.ui.approved;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApprovalsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ApprovalsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}