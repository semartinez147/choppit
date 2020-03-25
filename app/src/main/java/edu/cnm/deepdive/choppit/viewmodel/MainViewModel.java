package edu.cnm.deepdive.choppit.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import edu.cnm.deepdive.choppit.controller.ui.editing.SelectionFragment;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.model.pojo.RecipeWithDetails;
import edu.cnm.deepdive.choppit.model.repository.RecipeRepository;
import edu.cnm.deepdive.choppit.service.JsoupRetriever;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.annotation.Nonnull;
import org.jsoup.nodes.Document;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MutableLiveData<Recipe> recipe;
  private final MutableLiveData<List<Step>> steps;
  private final MutableLiveData<List<Ingredient>> ingredients;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Set<String>> permissions;
  private final MutableLiveData<String> status;
  private final CompositeDisposable pending;
  private final RecipeRepository repository;
  private final JsoupRetriever retriever;

  public MainViewModel(@NonNull Application application) {
    super(application);
    repository = RecipeRepository.getInstance();
    retriever = JsoupRetriever.getInstance();
    steps = new MutableLiveData<>();
    ingredients = new MutableLiveData<>();
    recipe = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    permissions = new MutableLiveData<>(new HashSet<>());
    pending = new CompositeDisposable();
    status = new MutableLiveData<>();
    resetData();
  }

  public LiveData<List<Recipe>> getAllRecipes() {
    return repository.getAll();
  }

  public LiveData<Recipe> getRecipe() {
    return recipe;
  }

  public LiveData<List<Step>> getSteps() {
    return steps;
  }

  public LiveData<List<Ingredient>> getIngredients() {
    return ingredients;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public LiveData<Set<String>> getPermissions() {
    return permissions;
  }

  public MutableLiveData<String> getStatus() {
    return status;
  }

  public void resetData() {
    steps.postValue(null);
    ingredients.postValue(null);
    recipe.postValue(null);
    throwable.postValue(null);
    status.postValue("");
  }

  public void makeItGo(String url) {
    Log.d("MVM", "starting makeItGo");
    throwable.setValue(null);
    status.postValue("connecting");
    pending.add(
        repository.connect(url)
            .subscribe(
                () -> status.postValue("gathering"),
                throwable::postValue
            )
    );
  }

  //                steps.postValue(repository.process(ingredient, instruction)),


  public void processData(String ingredient, String instruction) {
    Log.d("MVM", "beginning processData");
    throwable.setValue(null);
    status.postValue("processing");
    pending.add(
        repository.process(ingredient, instruction)
        .subscribe(steps::postValue)
    );
  }

  public void finish(List<Step> steps) {
    List<Ingredient> extract = new ArrayList<>();
    List<Ingredient> extracted = new ArrayList<>();
    for (Step step : steps) {
      extract.addAll(step.getIngredients());

    }
    for (Ingredient ex : extract) {
      if (!extracted.contains(ex)){
      extracted.add(ex);
      }
    }
    ingredients.postValue(extracted);
  }

  public void grantPermission(String permission) {
    Set<String> permissions = this.permissions.getValue();
    if (permissions.add(permission)) {
      this.permissions.setValue(permissions);
    }
  }

  public void revokePermission(String permission) {
    Set<String> permissions = this.permissions.getValue();
    if (permissions.remove(permission)) {
      this.permissions.setValue(permissions);
    }
  }


  @SuppressWarnings("unused")
  @OnLifecycleEvent(Event.ON_STOP)
  private void disposePending() {
    pending.clear();
  }
}
