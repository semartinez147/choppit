package edu.cnm.deepdive.choppit.model.repository;

import android.app.Application;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RecipeRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final ChoppitDatabase database;
  private final Executor networkPool;

  private static Application context;

  private RecipeRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = ChoppitDatabase.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
  }

  public static void setContext(Application context) {
    RecipeRepository.context = context;
  }

  public static RecipeRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public List<Recipe> favList() {
    RecipeDao dao = database.getRecipeDao();
    return dao.favList();
  }

  public List<Recipe> editedist() {
    RecipeDao dao = database.getRecipeDao();
    return dao.editedList();
  }

  public Single<Recipe> get(String title) {
    RecipeDao dao = database.getRecipeDao();
    return dao.select(title)
        .subscribeOn(Schedulers.io());
  }

  private static class InstanceHolder {

    private static final RecipeRepository INSTANCE = new RecipeRepository();
  }

}
