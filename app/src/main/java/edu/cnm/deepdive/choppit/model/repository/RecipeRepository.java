package edu.cnm.deepdive.choppit.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;
import edu.cnm.deepdive.choppit.service.JsoupRetriever;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * The repository handles requests from the {@link edu.cnm.deepdive.choppit.viewmodel.MainViewModel}
 * and interacts with the {@link JsoupRetriever}.
 */
public class RecipeRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final ChoppitDatabase database;
  private final Executor networkPool;
  private static Application context;
  private JsoupRetriever retriever;
  private String[] recipeMeta = new String[2];

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
  }

  /**
   * @return all {@link Recipe}s for display in the {@link edu.cnm.deepdive.choppit.controller.ui.cookbook.CookbookFragment}.
   */
  public LiveData<List<Recipe>> getAll() {
    return database.getRecipeDao().select();
  }


  /**
   * Not implemented yet.  Will be used for search/sort in the {@link
   * edu.cnm.deepdive.choppit.controller.ui.cookbook.CookbookFragment}.
   *
   * @return {@link Recipe}s where {@link Recipe#isFavorite()} is true.
   */
  public LiveData<List<Recipe>> favList() {
    RecipeDao dao = database.getRecipeDao();
    return dao.favList();
  }

  /**
   * Not implemented yet.  Will be used for search/sort in the {@link
   * edu.cnm.deepdive.choppit.controller.ui.cookbook.CookbookFragment}.
   *
   * @return a {@link Recipe} with a matching {@link Recipe#getTitle()}.
   */
  public Maybe<Recipe> getOne(String title) {
    RecipeDao dao = database.getRecipeDao();
    return dao.select(title)
        .subscribeOn(Schedulers.io());
  }

  /**
   * Calls the {@link Runnable} that processes the HTTP request.  Subscribes on {@link Thread}s from
   * the {@link #networkPool} to avoid touching the UI.  There may be a more direct way to do this.
   *
   * @param url is input by the user in {@link edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment}
   * @return a {@link Completable} handled by the {@link edu.cnm.deepdive.choppit.viewmodel.MainViewModel}.
   */
  public Completable connect(String url) {
    return Completable.fromRunnable(jsoup(url))
        .subscribeOn(Schedulers.from(networkPool));
  }

  /**
   * This {@link Runnable} is handled by {@link #connect(String)}.  It receives the user's url,
   * sends the {@link Document} returned by {@link Jsoup} to {@link JsoupRetriever} for processing,
   * and stores the {@link Document#title()} and url.
   *
   * @param url
   * @return
   */
  private Runnable jsoup(String url) {
    return () -> {
      Document doc = null;
      try {
        doc = Jsoup.connect(url).get();
      } catch (IOException e) {
        e.printStackTrace();
      }
      retriever.setDocument(doc);
      recipeMeta = new String[]{url, doc.title()};
    };
  }

  /**
   * This method is called by the {@link edu.cnm.deepdive.choppit.viewmodel.MainViewModel} and
   * passes the user inputs to the {@link JsoupRetriever} for processing.  The resulting data is
   * passed back up to the {@link edu.cnm.deepdive.choppit.viewmodel.MainViewModel} and eventually
   * displayed in the {@link edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment}.
   *
   * @param ingredient  input by the user on the {@link edu.cnm.deepdive.choppit.controller.ui.editing.SelectionFragment}
   * @param instruction input by the user on the {@link edu.cnm.deepdive.choppit.controller.ui.editing.SelectionFragment}
   * @return a list of {@link Step} objects with attached {@link edu.cnm.deepdive.choppit.model.entity.Ingredient}s.
   */
  public Single<List<Step>> process(String ingredient, String instruction) {
    List<Step> steps = retriever.process(ingredient, instruction);
    return Single.just(steps);
  }


  private static class InstanceHolder {

    private static final RecipeRepository INSTANCE = new RecipeRepository();
  }

}
