package edu.cnm.deepdive.choppit.model.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.model.pojo.RecipeWithDetails;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import edu.cnm.deepdive.choppit.service.JsoupRetriever;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class RecipeRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final ChoppitDatabase database;
  private final Executor networkPool;
  private static Application context;
  private JsoupRetriever retriever;

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
    retriever = JsoupRetriever.getInstance();
  }

  public LiveData<List<Recipe>> getAll() {
    return database.getRecipeDao().select();
  }

  public LiveData<List<RecipeWithDetails>> getAllDetails() {
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

  public Completable connect(String url) {
    return Completable.fromRunnable(jsoup(url))
        .subscribeOn(Schedulers.from(networkPool));
  }

  private Runnable jsoup(String url) {
    return () -> {
      Document doc = null;
      try {
        doc = Jsoup.connect(url).get();
      } catch (IOException e) {
        e.printStackTrace();
      }
      retriever.setDocument(doc);
    };
  }

  public Single<List<Step>> process(String ingredient, String instruction) {
    List<Step> steps = retriever.process(ingredient, instruction);
    return Single.just(steps);
  }


  private static class InstanceHolder {

    private static final RecipeRepository INSTANCE = new RecipeRepository();
  }

}
