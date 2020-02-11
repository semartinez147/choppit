package edu.cnm.deepdive.choppit;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;

public class ChoppitApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    ChoppitDatabase.setContext(this);
    // which database is which? new Thread(() -> ChoppitDatabase.getInstance().getChoppitDao().delete()).start();
  }
}
