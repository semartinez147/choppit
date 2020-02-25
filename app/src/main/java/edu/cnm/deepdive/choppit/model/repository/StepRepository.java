package edu.cnm.deepdive.choppit.model.repository;

import android.app.Application;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
import edu.cnm.deepdive.choppit.model.dao.StepDao;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StepRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final ChoppitDatabase database;
  private final Executor networkPool;

  private static Application context;

  private StepRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = ChoppitDatabase.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
  }

  public static void setContext(Application context) {
    StepRepository.context = context;
  }

  public static StepRepository getInstance() {
    return StepRepository.InstanceHolder.INSTANCE;
  }

  public List<Step> list(long id) {
    StepDao dao = database.getStepDao();
    return dao.list(id);
  }

  public Single<Step> get(long id) {
    StepDao dao = database.getStepDao();
    return dao.select(id);
  }

  private static class InstanceHolder {

    private static final StepRepository INSTANCE = new StepRepository();
  }

}
