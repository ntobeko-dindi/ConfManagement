package com.ntobeko.confmanagement.ui.postnewsarticle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PostNewsArticleViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PostNewsArticleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}