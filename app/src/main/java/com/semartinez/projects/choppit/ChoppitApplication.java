package com.semartinez.projects.choppit;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.semartinez.projects.choppit.model.repository.RecipeRepository;
import com.semartinez.projects.choppit.model.repository.StyleRepository;
import com.semartinez.projects.choppit.service.ChoppitDatabase;


public class ChoppitApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    RecipeRepository.setContext(this);
    StyleRepository.setContext(this);
    ChoppitDatabase.setContext(this);
    ChoppitDatabase.getInstance().getRecipeDao().delete();
  }


}
