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
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import com.semartinez.projects.choppit.model.pojo.RecipePojo;
import com.semartinez.projects.choppit.model.repository.RecipeRepository;
import com.semartinez.projects.choppit.service.DocumentWithStrings;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MutableLiveData<Recipe> recipe;
  private final MutableLiveData<List<Step>> steps;
  private final MutableLiveData<List<Ingredient>> ingredients;
  private final MutableLiveData<File> html;
  private final MutableLiveData<DocumentWithStrings> documentWithStrings;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Set<String>> permissions;
  private final MutableLiveData<String> status;
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
    html = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    permissions = new MutableLiveData<>(new HashSet<>());
    pending = new CompositeDisposable();
    status = new MutableLiveData<>();
    repository = RecipeRepository.getInstance();
    documentWithStrings = new MutableLiveData<>();
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

  public LiveData<DocumentWithStrings> getDocumentWithStrings() {
    return documentWithStrings;
  }

  public LiveData<File> getHtml() {
    return html;
  }

  public LiveData<String> getStatus() {
    return status;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public LiveData<Set<String>> getPermissions() {
    return permissions;
  }

  /**
   * Returns all {@link MutableLiveData} values to default to clear data from prior activities.
   */
  public void resetData() {
    Log.d("Choppit", "MVM resetData");
    steps.postValue(null);
    ingredients.postValue(null);
    recipe.postValue(null);
    documentWithStrings.postValue(null);
    html.postValue(null);
    throwable.postValue(null);
    status.postValue("");
  }

  public void resetStatus() {
    status.postValue("");
    throwable.postValue(null);
  }

  /**
   * This method is called by the {@link com.semartinez.projects.choppit.controller.ui.editing.LoadingFragment}
   * to begin processing.  It passes the user's url to {@link RecipeRepository#connect}, and
   * notifies the Loading Fragment before and after using {@link #status} values.
   *
   * @param url is taken from user input on the {@link com.semartinez.projects.choppit.controller.ui.home.HomeFragment}.
   */
  public void makeItGo(String url) {
    throwable.setValue(null);
    status.postValue("connecting");
    pending.add(
        repository.connect(url)
            .subscribe(
                () -> {
                  status.postValue("connected");
                  generateHtml();
                },
                throwable::postValue
            )
    );
  }

  public void generateHtml() {
    pending.add(
        repository.generateDocument()
            .subscribe(
                value -> {
                  documentWithStrings.postValue(value);
                  status.postValue("generated");
                },
                throwable::postValue
            )
    );
  }

  public void processData(String ingredient, String instruction) {
    Log.d("MVM", "beginning processData");
    throwable.setValue(null);
    status.postValue("processing");
    pending.add(
        repository.process(ingredient, instruction)
            .subscribe(this::finish)
    );
  }

  public void finish(RecipePojo data) {
    status.postValue("finishing");
    ingredients.setValue(data.getIngredients());
    steps.setValue(data.getSteps());
    postRecipe();
  }

  public void postRecipe() {
    this.recipe
        .postValue(new Recipe(repository.getRecipeUrl(), repository.getRecipeTitle(), false,
            steps.getValue(), ingredients.getValue()));
    status.postValue("finished");
  }

  public void saveRecipe(Recipe newRecipe) {
    throwable.setValue(null);
    repository.saveNew(newRecipe)
        .doOnError(throwable::postValue)
        .subscribe();
  }

  public void updateRecipe(Recipe recipe) {
    repository.update(recipe)
        .doOnError(throwable::postValue)
        .subscribeOn(Schedulers.io())
        .subscribe();
  }

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


  public String getSharedUrl() {
    return sharedUrl;
  }

  public void setSharedUrl(String string) {
    this.sharedUrl = string;
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void disposePending() {
    pending.clear();
  }
}
