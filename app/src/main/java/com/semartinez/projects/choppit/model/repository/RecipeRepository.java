package com.semartinez.projects.choppit.model.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;
import com.semartinez.projects.choppit.controller.exception.ConnectionFailureException;
import com.semartinez.projects.choppit.controller.ui.cookbook.CookbookFragment;
import com.semartinez.projects.choppit.controller.ui.editing.EditingFragment;
import com.semartinez.projects.choppit.controller.ui.editing.SelectionFragment;
import com.semartinez.projects.choppit.model.dao.RecipeDao;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Recipe.RecipeComponent;
import com.semartinez.projects.choppit.model.entity.Step;
import com.semartinez.projects.choppit.service.ChoppitDatabase;
import com.semartinez.projects.choppit.service.JsoupPrepper;
import com.semartinez.projects.choppit.service.JsoupRetriever;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * The repository handles requests from the {@link com.semartinez.projects.choppit.viewmodel.MainViewModel}
 * and interacts with the {@link JsoupRetriever}.
 */
public class RecipeRepository implements SharedPreferences.OnSharedPreferenceChangeListener {

  private static final int NETWORK_THREAD_COUNT = 10;

  private static Application context;
  private final ChoppitDatabase database;
  private final Executor networkPool;
  private final JsoupRetriever retriever;
  private final JsoupPrepper prepper;
  private final SharedPreferences preferences;
  private String[] recipeMeta = new String[2];
  private Document doc;

  public static void setContext(Application context) {
    RecipeRepository.context = context;
  }

  public static RecipeRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public String[] getRecipeMeta() {
    return recipeMeta;
  }

  private RecipeRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = ChoppitDatabase.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
    retriever = JsoupRetriever.getInstance();
    prepper = JsoupPrepper.getInstance();
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.registerOnSharedPreferenceChangeListener(this);
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

  /**
   * @return all {@link Recipe}s for display in the {@link com.semartinez.projects.choppit.controller.ui.cookbook.CookbookFragment}.
   */
  public LiveData<List<Recipe>> getAll() {
    return database.getRecipeDao().select();
  }


  /**
   * Not implemented yet.  Will be used for search/sort in the {@link
   * com.semartinez.projects.choppit.controller.ui.cookbook.CookbookFragment}.
   *
   * @return {@link Recipe}s where {@link Recipe#isFavorite()} is true.
   */
  public LiveData<List<Recipe>> favList() {
    RecipeDao dao = database.getRecipeDao();
    return dao.favList();
  }

  /**
   * Not implemented yet.  Will be used for search/sort in the {@link CookbookFragment}.
   *
   * @param title a search string.
   * @return a {@link Recipe} with a matching {@link Recipe#getTitle()}.
   */
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

  /**
   * Calls the {@link Runnable} that processes the HTTP request.  Subscribes on {@link Thread}s from
   * the {@link #networkPool} to avoid touching the UI.  There may be a more direct way to do this.
   *
   * @param url is input by the user in {@link com.semartinez.projects.choppit.controller.ui.home.HomeFragment}
   * @return a {@link Completable} handled by the {@link com.semartinez.projects.choppit.viewmodel.MainViewModel}.
   */
  public Completable connect(String url) {
    Log.d("Choppit", " Repository connect method");
    return Completable.fromRunnable(() -> {
      doc = null;
      try {
        doc = Jsoup.connect(url).get();
      } catch (Exception e) {
        Log.e("Choppit", e.toString() + " in Repository jsoup Runnable");
        throw new ConnectionFailureException();
      }
      assert doc != null : "null document";
      retriever.setDocument(doc);
      prepper.setDocument(doc);
      recipeMeta = new String[]{url, doc.title()};
    })
        .subscribeOn(Schedulers.from(networkPool));
  }

  public Single<File> generateHtml() {
    File html;
    try {
      html = prepper.prepare(recipeMeta[0]);
    } catch (IOException e) {
      return Single.error(e);
    }
    return Single.just(html);
  }

  /**
   * This method is called by the {@link MainViewModel} and passes the user inputs to the {@link
   * JsoupRetriever} for processing.  The resulting data is passed back up to the {@link
   * MainViewModel} and eventually displayed in the {@link EditingFragment}.
   *
   * @param ingredient  input by the user on the {@link SelectionFragment}
   * @param instruction input by the user on the {@link SelectionFragment}
   * @return a list of {@link Step} objects with attached {@link Ingredient}s.
   */
  public Single<Map<String, List<? extends RecipeComponent>>> process(String ingredient,
      String instruction) {
    Map<String, List<? extends RecipeComponent>> data = retriever.process(ingredient, instruction);
    return Single.just(data);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
/* Not fully implemented yet.

    Resources.Theme theme = context.getTheme();
    if (key.equals(context.getString(R.string.sp_key_recipe_font_style))) {
      switch (sharedPreferences
          .getString(context.getString(R.string.sp_key_recipe_font_style), "Droid")) {
        case "Droid":
          theme.applyStyle(R.style.Droid, true);
          break;
        case "Lato":
          theme.applyStyle(R.style.Lato, true);
          break;
        case "Lora":
          theme.applyStyle(R.style.Lora, true);
          break;
        case "Quicksand":
          theme.applyStyle(R.style.Quicksand, true);
          break;
        case "Roboto":
          theme.applyStyle(R.style.Roboto, true);
          break;
      }
    }
*/
  }

  private static class InstanceHolder {

    private static final RecipeRepository INSTANCE = new RecipeRepository();
  }

}
