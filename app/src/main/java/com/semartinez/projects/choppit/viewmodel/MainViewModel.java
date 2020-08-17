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
import com.semartinez.projects.choppit.controller.ui.editing.LoadingFragment;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import com.semartinez.projects.choppit.model.repository.RecipeRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MutableLiveData<Recipe> recipe;
  private final MutableLiveData<List<Step>> steps;
  private final MutableLiveData<List<Ingredient>> ingredients;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Set<String>> permissions;
  private final MutableLiveData<String> status;
  private final CompositeDisposable pending;
  private final RecipeRepository repository;

  /**
   * Initializes the MainViewModel and the variables it contains.
   *
   * @param application this application.
   */
  public MainViewModel(@NonNull Application application) {
    super(application);
    repository = RecipeRepository.getInstance();
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

  public MutableLiveData<String> getStatus() {
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
    steps.postValue(null);
    ingredients.postValue(null);
    recipe.postValue(null);
    throwable.postValue(null);
    status.postValue("");
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
                () -> status.postValue("gathering"),
                throwable::postValue
            )
    );
  }

  /**
   * This method is called by the {@link com.semartinez.projects.choppit.controller.ui.editing.LoadingFragment}
   * when it receives the {@link #status} data indicating {@link #makeItGo(String)} has completed
   * successfully.  It passes parameters into {@link RecipeRepository#process(String, String)},
   * notifies the {@link com.semartinez.projects.choppit.controller.ui.editing.LoadingFragment} via {@link
   * #status}, and posts the resulting {@link List} of {@link Step}s to {@link #steps}.
   *
   * @param ingredient  from user input on the {@link com.semartinez.projects.choppit.controller.ui.editing.SelectionFragment}.
   * @param instruction from user input on the {@link com.semartinez.projects.choppit.controller.ui.editing.SelectionFragment}.
   */
  public void processData(String ingredient, String instruction) {
    Log.d("MVM", "beginning processData");
    throwable.setValue(null);
    status.postValue("processing");
    pending.add(
        repository.process(ingredient, instruction)
            .subscribe(steps::postValue)
    );
  }

  /**
   * This method is called by the {@link com.semartinez.projects.choppit.controller.ui.editing.LoadingFragment}
   * when it sees {@link #steps} update, indicating {@link #processData(String, String)} has
   * completed successfully.  This method ensures that there are no duplicate values in {@link
   * #ingredients} before updating it.
   *
   * @param steps retrieved from {@link #steps} when the method is called.
   */
  public void finish(List<Step> steps) {
    List<Ingredient> extract = new ArrayList<>();
    List<Ingredient> extracted = new ArrayList<>();
    for (Step step : steps) {
      extract.addAll(step.getIngredients());

    }
    for (Ingredient ex : extract) {
      if (!extracted.contains(ex)) {
        extracted.add(ex);
      }
    }
    ingredients.postValue(extracted);
    status.postValue("finishing");
  }

  /**
   * This method adds the url and title to a {@link Recipe} from the {@link LoadingFragment}, then
   * posts it to the {@link #recipe} field, which signals the {@link LoadingFragment} to navigate
   * forward.
   */
  public void addRecipe() {
    Recipe recipe = new Recipe();
    recipe.setUrl(repository.getRecipeMeta()[0]);
    recipe.setTitle(repository.getRecipeMeta()[1]);
    recipe.setSteps(steps.getValue());
    this.recipe.postValue(recipe);
    status.postValue("finishing");
  }

  public void saveRecipe(Recipe newRecipe) {
    throwable.setValue(null);
    if (newRecipe.getRecipeId() == 0) {
      repository.save(newRecipe)
          .doOnError(throwable::postValue)
          .subscribe();
    } else {
      repository.update(newRecipe)
          .doOnError(throwable::postValue)
          .subscribe();
    }
  }

  public void loadRecipe(Long id) {
    throwable.setValue(null);
    pending.add(
        repository.getById(id)
            .subscribe(
                recipe::postValue,
                throwable::postValue
            )
    );
  }


  // switch back to public when implemented
  @SuppressWarnings("unused")
  private void grantPermission(String permission) {
    Set<String> permissions = this.permissions.getValue();
    assert permissions != null;
    if (permissions.add(permission)) {
      this.permissions.setValue(permissions);
    }
  }

  // switch back to public when implemented
  public void revokePermission(String permission) {
    Set<String> permissions = this.permissions.getValue();
    assert permissions != null;
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
