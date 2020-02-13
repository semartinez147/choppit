package edu.cnm.deepdive.choppit;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import io.reactivex.schedulers.Schedulers;


public class ChoppitApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    ChoppitDatabase.setContext(this);
    ChoppitDatabase.getInstance().getRecipeDao().delete()
        .subscribeOn(Schedulers.io())
        .subscribe();
  }
}
