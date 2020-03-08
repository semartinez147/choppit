package edu.cnm.deepdive.choppit.controller.ui.cookbook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CookbookViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public CookbookViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is notifications fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }
}