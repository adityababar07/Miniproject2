package com.example.mini_project.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Intrusion System disabled\n as it is day time or the lights are on !!!!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}