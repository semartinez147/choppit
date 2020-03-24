package edu.cnm.deepdive.choppit.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.choppit.model.dao.IngredientDao;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import edu.cnm.deepdive.choppit.service.JsoupRetriever;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class IngredientRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final ChoppitDatabase database;
  private final Executor networkPool;

  private static Application context;
  private JsoupRetriever retriever;

  private IngredientRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = ChoppitDatabase.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
    retriever = JsoupRetriever.getInstance();
  }

  public static void setContext(Application context) {
    IngredientRepository.context = context;
  }

  public static IngredientRepository getInstance() {
    return IngredientRepository.InstanceHolder.INSTANCE;
  }

  public LiveData<List<Ingredient>> list() {
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
