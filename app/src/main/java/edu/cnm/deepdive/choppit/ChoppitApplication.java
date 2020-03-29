package edu.cnm.deepdive.choppit;

import android.app.Application;
import com.facebook.stetho.Stetho;
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

    ChoppitDatabase.getInstance().getRecipeDao().check()
        .subscribeOn(Schedulers.io())
        .doOnError(error -> preloadDatabase())
        .subscribe();

    preloadDatabase();
  }

  private void preloadDatabase() {
    for (Recipe recipe : Arrays.asList(Recipe.populateData())) {
      for (Step step : recipe.getSteps()) {
        step.setRecipeId(recipe.getId());
        for (Ingredient ingredient : step.getIngredients()) {
          ingredient.setStepId(step.getStepId());
        }
      }
      ChoppitDatabase.getInstance().getRecipeDao().insert(recipe).subscribeOn(Schedulers.io()).subscribe();
    }
  }
}
