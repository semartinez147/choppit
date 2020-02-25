package edu.cnm.deepdive.choppit.model.repository;

import android.app.Application;
import edu.cnm.deepdive.choppit.model.dao.ItemDao;
import edu.cnm.deepdive.choppit.model.entity.Item;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ItemRepository {


  private static final int NETWORK_THREAD_COUNT = 10;

  private final ChoppitDatabase database;
  private final Executor networkPool;

  private static Application context;

  private ItemRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = ChoppitDatabase.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
  }

  public static void setContext(Application context) {
    ItemRepository.context = context;
  }

  public static ItemRepository getInstance() {
    return ItemRepository.InstanceHolder.INSTANCE;
  }

  public List<Item> get() {
    ItemDao dao = database.getItemDao();
    return dao.select();
  }

  public Single<Item> get(long id) {
    ItemDao dao = database.getItemDao();
    return dao.select(id)
        .subscribeOn(Schedulers.io());
  }

  private static class InstanceHolder {

    private static final ItemRepository INSTANCE = new ItemRepository();
  }

}
