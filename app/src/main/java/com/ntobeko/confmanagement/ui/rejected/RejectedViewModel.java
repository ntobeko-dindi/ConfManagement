package com.ntobeko.confmanagement.ui.rejected;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RejectedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RejectedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}