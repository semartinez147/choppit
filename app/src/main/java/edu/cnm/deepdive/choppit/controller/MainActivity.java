package edu.cnm.deepdive.choppit.controller;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.InfoFragment;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;

/**
 * This activity houses the UI {@link androidx.fragment.app.Fragment}s.  It also establishes {@link
 * #onOptionsItemSelected(MenuItem)} behavior.  Help button {@link android.app.AlertDialog} contents
 * is set by the label of the active {@link androidx.fragment.app.Fragment} when the button is
 * pressed.
 */
public class MainActivity extends AppCompatActivity {

  private NavOptions navOptions;
  private NavController navController;
  static ActionBar actionBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    actionBar = getActionBar();
    setupNavigation();
    setupViewModel();
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
    switch (item.getItemId()) {
      case R.id.help:
        showInfo(navController.getCurrentDestination().getId(),
            navController.getCurrentDestination().getLabel().toString());
        break;
      case R.id.text_options:
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        break;
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

/*  @Override
  public void onBackPressed() {
    if (navController.getCurrentDestination().getId() == R.id.navigation_editing) {
      navigateTo(R.id.navigation_selection);
    }
    if (navController.getCurrentDestination().getId() != R.id.navigation_home) {
      navigateTo(R.id.navigation_home);
    } else {
      Intent a = new Intent(Intent.ACTION_MAIN);
      a.addCategory(Intent.CATEGORY_HOME);
      a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(a);
    }
  }*/

  private void setupNavigation() {
    navOptions = new NavOptions.Builder()
        .setPopUpTo(R.id.navigation_home, true)
        .build();
    navController = Navigation.findNavController(this, R.id.nav_host_fragment);
  }

  /**
   * Useful for displaying error messages.
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
    getLifecycle().addObserver(viewModel);
  }

  /**
   * Sets up basic navigation behavior.
   * @param itemId must not match the destination, or there is no need to navigate.
   */
  public void navigateTo(int itemId) {
    if (navController.getCurrentDestination().getId() != itemId) {
      navController.navigate(itemId, null, navOptions);
    }
  }

  private void showInfo(int currentFragment, String fragmentLabel) {
    new InfoFragment(currentFragment, fragmentLabel)
        .show(getSupportFragmentManager(), InfoFragment.class.getName());
  }
}
