package com.semartinez.projects.choppit.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import com.semartinez.projects.choppit.model.entity.AssemblyRecipe;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import com.semartinez.projects.choppit.model.repository.RecipeRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of AndroidViewModel.  MainViewModel handles asynchronous calls to {@link
 * RecipeRepository} methods and contains various {@link MutableLiveData} fields.
 */
public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MutableLiveData<Recipe> recipe;
  private final MutableLiveData<List<Step>> steps;
  private final MutableLiveData<List<Ingredient>> ingredients;
  private final MutableLiveData<List<String>> stringsFromDocument;
  private final MutableLiveData<State> status;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Set<String>> permissions;
  private final CompositeDisposable pending;
  private final RecipeRepository repository;
  private String sharedUrl = null;

  /**
   * Initializes the MainViewModel and the variables it contains.
   *
   * @param application this application.
   */
  public MainViewModel(@NonNull Application application) {
    super(application);
    recipe = new MutableLiveData<>();
    steps = new MutableLiveData<>();
    ingredients = new MutableLiveData<>();
    stringsFromDocument = new MutableLiveData<>();
    status = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    permissions = new MutableLiveData<>(new HashSet<>());
    pending = new CompositeDisposable();
    repository = RecipeRepository.getInstance();
    resetData();
  }

  /**
   * @return LiveData containing all Recipes returned from the database by {@link
   * RecipeRepository#getAll()}.
   */
  public LiveData<List<Recipe>> getAllRecipes() {
    return repository.getAll();
  }

  /**
   * @return LiveData containing a single Recipe.
   */
  public LiveData<Recipe> getRecipe() {
    return recipe;
  }

  /**
   * @return LiveData containing a list of Steps.
   */
  public LiveData<List<Step>> getSteps() {
    return steps;
  }

  /**
   * @return LiveData containing a list of Ingredients.
   */
  public LiveData<List<Ingredient>> getIngredients() {
    return ingredients;
  }

  /**
   * @return LiveData containing a list of Strings
   */
  public LiveData<List<String>> getStringsFromDocument() {
    return stringsFromDocument;
  }

  /**
   * @return LiveData containing a State.
   */
  public LiveData<State> getStatus() {
    return status;
  }

  /**
   * @return LiveData containing a Throwable
   */
  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * @return a URL received by an Android Intent.
   */
  public String getSharedUrl() {
    return sharedUrl;
  }

  /**
   * @param string a URL received by an Android Intent.
   */
  public void setSharedUrl(String string) {
    this.sharedUrl = string;
  }

  private LiveData<Set<String>> getPermissions() {
    return permissions;
  }

  /**
   * Returns all {@link MutableLiveData} values (except Status) to default to clear data from prior
   * activities.
   */
  public void resetData() {
    steps.postValue(null);
    ingredients.postValue(null);
    recipe.postValue(null);
    stringsFromDocument.postValue(null);
    throwable.postValue(null);
  }

  /**
   * Returns Status to default to clear progress from prior activities.
   */
  public void resetStatus() {
    status.postValue(State.READY);
    throwable.postValue(null);
  }

  /**
   * This method is called by the {@link com.semartinez.projects.choppit.controller.ui.editing.LoadingFragment}
   * on navigation from the HomeFragment to begin processing.  It passes the user's url to {@link
   * RecipeRepository#connect}, and notifies the Loading Fragment by posting {@link #status} values
   * as MutableLiveData.
   *
   * On success, calls {@link #generateStrings()} and updates status.  On failure, a Throwable is posted instead.
   *
   * @param url is taken from user input on the {@link com.semartinez.projects.choppit.controller.ui.home.HomeFragment}.
   */
  public void makeItGo(String url) {
    throwable.setValue(null);
    status.setValue(State.CONNECTING);
    pending.add(
        repository.connect(url)
            .subscribe(
                () -> {
                  status.postValue(State.CONNECTED);
                  generateStrings();
                },
                throwable::postValue
            )
    );
  }

  /**
   * This method is called if {@link #makeItGo(String)} completes successfully. It calls {@link
   * RecipeRepository#generateStrings()}.
   * <p>
   * On success, post the resulting Strings as MutableLiveData and updates status.  On failure, a Throwable is posted
   * instead.
   */
  public void generateStrings() {
    pending.add(
        repository.generateStrings()
            .subscribe(
                value -> {
                  stringsFromDocument.postValue(value);
                  status.postValue(State.GENERATED);
                },
                throwable::postValue
            )
    );
  }

  /**
   * This method is called by the {@link com.semartinez.projects.choppit.controller.ui.editing.SelectionFragment}
   * when navigating back to the LoadingFragment.  It passes user-selected Strings to the backend to
   * help identify what text should be extracted from the HTML.
   * <p>
   * On success, {@link #finish(AssemblyRecipe)} is called and the next status value is posted.  On
   * failure, a Throwable is posted.
   *
   * @param ingredient  user-selected ingredient text.
   * @param instruction user-selected instruction text.
   */
  public void processData(String ingredient, String instruction) {
    Log.d("MVM", "beginning processData");
    status.setValue(State.PROCESSING);
    throwable.setValue(null);
    pending.add(
        repository.process(ingredient, instruction)
            .subscribe(data -> {
                  status.postValue(State.FINISHING);
                  finish(data);
                },
                throwable::postValue)
    );
  }

  /**
   * Posts a completed Recipe and the final status value.
   *
   * @param data a completed AssemblyRecipe.
   */
  public void finish(AssemblyRecipe data) {
    recipe.postValue(new Recipe(data));
    status.postValue(State.FINISHED);
  }

  /**
   * @param newRecipe is a Recipe to be saved as a new database entity.
   */
  public void saveRecipe(Recipe newRecipe) {
    throwable.setValue(null);
    repository.saveNew(newRecipe)
        .doOnError(throwable::postValue)
        .subscribe();
  }

  /**
   * @param recipe is a Recipe to be updated in the database.
   */
  public void updateRecipe(Recipe recipe) {
    repository.update(recipe)
        .doOnError(throwable::postValue)
        .subscribeOn(Schedulers.io())
        .subscribe();
  }

  /**
   * On success, posts a retrieved Recipe.  On failure, a Throwable is posted.
   *
   * @param id is a RecipeId passed by either the EditingFragment or RecipeFragment.
   */
  public void loadRecipe(Long id) {
    throwable.setValue(null);
    pending.add(
        repository.loadDetails(id)
            .subscribe(
                recipe::postValue,
                throwable::postValue
            )
    );
  }

  public void deleteRecipe(Recipe r) {
    throwable.setValue(null);
    pending.add(repository.delete(r)
        .subscribe(
            value -> recipe.postValue(null),
            throwable::postValue
        )
    );
  }

  // TODO: switch back on when permissions are implemented
//  private void grantPermission(String permission) {
//    Set<String> permissions = this.permissions.getValue();
//    assert permissions != null;
//    if (permissions.add(permission)) {
//      this.permissions.setValue(permissions);
//    }
//  }
//
//  public void revokePermission(String permission) {
//    Set<String> permissions = this.permissions.getValue();
//    assert permissions != null;
//    if (permissions.remove(permission)) {
//      this.permissions.setValue(permissions);
//    }
//  }


  @OnLifecycleEvent(Event.ON_STOP)
  private void disposePending() {
    pending.clear();
  }

  public enum State {
    READY,
    CONNECTING,
    CONNECTED,
    GENERATED,
    PROCESSING,
    FINISHING,
    FINISHED

  }
}
