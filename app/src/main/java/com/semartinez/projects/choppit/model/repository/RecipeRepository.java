package com.semartinez.projects.choppit.model.repository;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.semartinez.projects.choppit.controller.exception.ConnectionFailureException;
import com.semartinez.projects.choppit.controller.ui.editing.EditingFragment;
import com.semartinez.projects.choppit.controller.ui.editing.SelectionFragment;
import com.semartinez.projects.choppit.model.dao.RecipeDao;
import com.semartinez.projects.choppit.model.entity.AssemblyRecipe;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import com.semartinez.projects.choppit.service.ChoppitDatabase;
import com.semartinez.projects.choppit.service.JsoupMachine;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * The repository handles requests from the {@link com.semartinez.projects.choppit.viewmodel.MainViewModel}
 * and interacts with the {@link JsoupMachine}.
 */
public class RecipeRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private static Application context;
  private final ChoppitDatabase database;
  private final Executor networkPool;
  private final JsoupMachine jsoupMachine;
  private Document doc;

  public static void setContext(Application context) {
    RecipeRepository.context = context;
  }

  public static RecipeRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private RecipeRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = ChoppitDatabase.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
    jsoupMachine = JsoupMachine.getInstance();

  }

  public Single<Recipe> saveNew(Recipe recipe) {
    return database.getRecipeDao().insert(recipe)
        .subscribeOn(Schedulers.io())
        .map(id -> {
          recipe.setRecipeId(id);
          for (Step step : recipe.getSteps()) {
            step.setRecipeId(recipe.getRecipeId());
            database.getStepDao().insert(step)
                .map(stepId -> {
                  step.setStepId(stepId);
                  return step;
                })
                .onErrorReturnItem(step)
                .subscribe();
          }
          for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipeId(recipe.getRecipeId());
            database.getIngredientDao().insert(ingredient)
                .map(ingredientId -> {
                  ingredient.setId(ingredientId);
                  return ingredient;
                })
                .onErrorReturnItem(ingredient)
                .subscribe();
          }
          return recipe;
        });
  }

  public Single<Integer> update(Recipe recipe) {
    return database.getRecipeDao().update(recipe).subscribeOn(Schedulers.io());
  }

  /**  * @return all {@link Recipe}s for display in the {@link com.semartinez.projects.choppit.controller.ui.cookbook.CookbookFragment}.
   */
  public LiveData<List<Recipe>> getAll() {
    return database.getRecipeDao().select();
  }


  public LiveData<List<Recipe>> favList() {
    RecipeDao dao = database.getRecipeDao();
    return dao.favList();
  }

  public Maybe<Recipe> getOne(String title) {
    RecipeDao dao = database.getRecipeDao();
    return dao.select(title)
        .subscribeOn(Schedulers.io());
  }

  public Single<Recipe> getById(Long id) {
    RecipeDao dao = database.getRecipeDao();
    return dao.getOne(id)
        .subscribeOn(Schedulers.io());
  }

  public Single<Recipe> loadDetails(long id) {
    RecipeDao dao = database.getRecipeDao();
    return dao.loadRecipeData(id)
        .subscribeOn(Schedulers.io()).map(Recipe::new);
  }

  public Single<Integer> delete(Recipe recipe) {
    RecipeDao dao = database.getRecipeDao();
    return dao.delete(recipe)
        .subscribeOn(Schedulers.io());
  }

  public Completable connect(String url) {
    Log.d("Choppit", " Repository connect method");
    return Completable.fromRunnable(() -> {
      doc = null;
      try {
        doc = Jsoup.connect(url).get();
      } catch (MalformedURLException e) {
        throw new ConnectionFailureException("Not a valid link");
      } catch (HttpStatusException e) {
        throw new ConnectionFailureException("There was a problem with the website.");
      } catch (IOException e) {
        throw new ConnectionFailureException("An unknown error occurred.  Please try again.");
      }
      assert doc != null : "null document";
    })
        .subscribeOn(Schedulers.from(networkPool));
  }


  public Single<List<String>> generateStrings() {
    return jsoupMachine.prepare(doc).subscribeOn(Schedulers.computation());
  }

  /**
   * This method is called by the {@link MainViewModel} and passes the user inputs to the {@link
   * JsoupMachine} for processing.  The resulting data is passed back up to the {@link
   * MainViewModel} and eventually displayed in the {@link EditingFragment}.
   *
   * @param ingredient  input by the user on the {@link SelectionFragment}
   * @param instruction input by the user on the {@link SelectionFragment}
   * @return a list of {@link Step} objects with attached {@link Ingredient}s.
   */
  public Single<AssemblyRecipe> process(String ingredient, String instruction) {
    return Single.just(jsoupMachine.process(ingredient, instruction)).subscribeOn(Schedulers.io());
  }

  private static class InstanceHolder {

    private static final RecipeRepository INSTANCE = new RecipeRepository();
  }

}
