package edu.cnm.deepdive.choppit.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.model.pojo.RecipeWithDetails;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import edu.cnm.deepdive.choppit.service.JsoupRetriever;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RecipeRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final JsoupRetriever retriever;
  private final ChoppitDatabase database;
  private final Executor networkPool;

  private static Application context;

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
    retriever = JsoupRetriever.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
  }

  private void buildRecipe() {

  }

public Single<List<Ingredient>> retrieveIngredients() {
    List<Ingredient> ingredients = retriever.ingredientBuilder();
    return Single.just(ingredients);
}

public Single<List<Step>> retrieveSteps() {
    List<Step> steps = retriever.stepBuilder();
    return Single.just(steps);
}


  public LiveData<List<RecipeWithDetails>> getAll() {
    return database.getRecipeDao().selectWithDetails();
  }

  public LiveData<List<Recipe>> favList() {
    RecipeDao dao = database.getRecipeDao();
    return dao.favList();
  }

  public LiveData<List<Recipe>> editedList() {
    RecipeDao dao = database.getRecipeDao();
    return dao.editedList();
  }

  public Maybe<Recipe> getOne(String title) {
    RecipeDao dao = database.getRecipeDao();
    return dao.select(title)
        .subscribeOn(Schedulers.io());
  }

  private static class InstanceHolder {

    private static final RecipeRepository INSTANCE = new RecipeRepository();
  }

}
