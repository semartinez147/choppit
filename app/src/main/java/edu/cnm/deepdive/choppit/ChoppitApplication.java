package edu.cnm.deepdive.choppit;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.model.repository.RecipeRepository;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;


public class ChoppitApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    RecipeRepository.setContext(this);
    ChoppitDatabase.setContext(this);

    ChoppitDatabase.getInstance().getRecipeDao().delete();

    preloadDatabase();
  }

  private void preloadDatabase() {
RecipeRepository repository = RecipeRepository.getInstance();
    for (Recipe recipe : Arrays.asList(Recipe.populateData())) {
      repository.save(recipe).subscribe();
    }
  }
}
