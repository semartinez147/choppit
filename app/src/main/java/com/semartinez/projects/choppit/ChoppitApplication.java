package com.semartinez.projects.choppit;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.semartinez.projects.choppit.model.repository.RecipeRepository;
import com.semartinez.projects.choppit.service.ChoppitDatabase;


/**
 * The starting point for the app.  Initializes Stetho for database inspection as well as the
 * repository and database classes.  Makes a delete request to ensure access to the database.
 */
public class ChoppitApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    RecipeRepository.setContext(this);
    ChoppitDatabase.setContext(this);
    ChoppitDatabase.getInstance().getRecipeDao().delete();
  }


}
