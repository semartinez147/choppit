package edu.cnm.deepdive.choppit.model.repository;

import android.app.Application;
import edu.cnm.deepdive.choppit.model.dao.IngredientDao;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class IngredientRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final ChoppitDatabase database;
  private final Executor networkPool;

  private static Application context;

  private IngredientRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = ChoppitDatabase.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
  }

  public static void setContext(Application context) {
    IngredientRepository.context = context;
  }

  public static IngredientRepository getInstance() {
    return IngredientRepository.InstanceHolder.INSTANCE;
  }

  public List<Ingredient> list() {
    IngredientDao dao = database.getIngredientDao();
    return dao.list();
  }

  public Single<Ingredient> get(long id) {
    IngredientDao dao = database.getIngredientDao();
    return dao.select(id)
        .subscribeOn(Schedulers.io());
  }


  private static class InstanceHolder {

    private static final IngredientRepository INSTANCE = new IngredientRepository();
  }

}
