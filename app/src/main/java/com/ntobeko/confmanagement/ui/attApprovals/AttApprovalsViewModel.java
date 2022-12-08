package com.ntobeko.confmanagement.ui.attApprovals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AttApprovalsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AttApprovalsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}