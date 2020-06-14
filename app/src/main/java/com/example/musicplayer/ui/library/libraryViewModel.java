package com.example.musicplayer.ui.library;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class libraryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public libraryViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}