package edu.cnm.deepdive.choppit.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.pojo.RecipeWithDetails;
import edu.cnm.deepdive.choppit.model.repository.RecipeRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MutableLiveData<Recipe> recipe;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Set<String>> permissions;
  private final CompositeDisposable pending;
  private final RecipeRepository repository;


  public MainViewModel(@NonNull Application application) {
    super(application);
    repository = RecipeRepository.getInstance();
    recipe = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    permissions = new MutableLiveData<>(new HashSet<>());
    pending = new CompositeDisposable();
  }

  public LiveData<List<RecipeWithDetails>> getAllRecipes() {
    return repository.getAll();
  }

  public LiveData<Recipe> getRecipe() {
    return recipe;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public LiveData<Set<String>> getPermissions() {
    return permissions;
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
