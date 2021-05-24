package com.semartinez.projects.choppit.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.ui.dialog.InfoDialog;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.repository.RecipeRepository;
import com.semartinez.projects.choppit.service.ChoppitDatabase;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import io.reactivex.schedulers.Schedulers;

/**
 * This activity houses the UI Fragments  It also establishes {@link
 * #onOptionsItemSelected(MenuItem)} behavior.  Help button {@link android.app.AlertDialog} contents
 * is set by the label of the active {@link androidx.fragment.app.Fragment} when the button is
 * pressed.
 */
public class MainActivity extends AppCompatActivity implements OnBackStackChangedListener {

  private NavController navController;
  private String sharedUrl = null;
  AppBarConfiguration appBarConfiguration;
  private final int help = R.id.help;

  /**
   * This override of onCreate receives a shared URL and prepares the UI, Navigation components and
   * ViewModel.  It also calls a method to generate filler recipes, but that method call will be
   * removed for production.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    String action = intent.getAction();
    String type = intent.getType();
    if (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type)) {
      handleSentText(intent);
    }

    setContentView(R.layout.activity_main);
    setupNavigation();
    setupViewModel();
    shouldDisplayHomeUp();

    //DEV fills several dummy Recipes into the database for testing & debugging.
    preloadDatabase();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.help_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;

    if (item.getItemId() == R.id.help) {
      showInfo(navController.getCurrentDestination().getId(),
          navController.getCurrentDestination().getLabel().toString());
    } else if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      handled = true;
    } else {
      handled = super.onOptionsItemSelected(item);
    }

    return handled;
  }

  private void handleSentText(Intent intent) {
    String text = intent.getStringExtra(Intent.EXTRA_TEXT);
    if (text != null) {
      sharedUrl = text;
    }
  }

  private void setupNavigation() {
    navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    navController.navigate(R.id.navigation_home);
  }

  /**
   * Useful for displaying error messages.
   *
   * @param message will be displayed in the Toast.
   */
  public void showToast(String message) {
    Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0,
        getResources().getDimensionPixelOffset(R.dimen.toast_vertical_margin));
    toast.show();
  }

  private void setupViewModel() {
    MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    if (sharedUrl != null) {
      viewModel.setSharedUrl(sharedUrl);
    }
    viewModel.getThrowable().observe(this, throwable -> {
      if (throwable != null) {
        showToast(throwable.getMessage());
        Log.d("MainActivityThrowable: ", throwable.toString());
        throwable.printStackTrace();
      }
    });
    getLifecycle().addObserver(viewModel);
  }

  private void showInfo(int currentFragment, String fragmentLabel) {
    new InfoDialog(currentFragment, fragmentLabel)
        .show(getSupportFragmentManager(), InfoDialog.class.getName());
  }

  private void preloadDatabase() {
    RecipeRepository repository = RecipeRepository.getInstance();
    ChoppitDatabase.getInstance().getRecipeDao().check().subscribeOn(Schedulers.io())
        .subscribe(
            (recipe -> {
            }),
            (throwable -> {
              for (Recipe recipe : Recipe.populateData()) {
                repository.saveNew(recipe).subscribe();
              }
            })
        );

  }


  @Override
  public void onBackStackChanged() {
    shouldDisplayHomeUp();
  }

  private void shouldDisplayHomeUp() {
    boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
    getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
  }

  @Override
  public boolean onSupportNavigateUp() {
    getSupportFragmentManager().popBackStack();

    return NavigationUI.navigateUp(navController, appBarConfiguration)
        || super.onSupportNavigateUp();
  }
}
