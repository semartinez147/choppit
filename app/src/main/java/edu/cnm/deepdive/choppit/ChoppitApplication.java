package edu.cnm.deepdive.choppit;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class ChoppitApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
  }
}
