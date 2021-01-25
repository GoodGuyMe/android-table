package com.table.tableapp.ui.colors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ColorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ColorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Get your colors here!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}