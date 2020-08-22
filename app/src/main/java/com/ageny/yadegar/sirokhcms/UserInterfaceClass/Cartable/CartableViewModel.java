package com.ageny.yadegar.sirokhcms.UserInterfaceClass.Cartable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartableViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CartableViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}