package com.table.tableapp.ui.speed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpeedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpeedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("SPEEEEEEEED!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}