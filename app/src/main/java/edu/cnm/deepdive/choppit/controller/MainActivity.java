package edu.cnm.deepdive.choppit.controller;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;
import edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

  private MainViewModel viewModel;
  private NavOptions navOptions;
  private NavController navController;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupNavigation();
//    setupViewModel();

    HomeFragment homeFragment = new HomeFragment();
    homeFragment.setArguments(getIntent().getExtras());
    FragmentManager fragManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragManager.beginTransaction();
    transaction.add(R.id.nav_host_fragment, homeFragment, "homeFragment");
    transaction.commit();

    // use to test other fragments
//    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//    fragmentTransaction.replace(R.id.nav_host_fragment, new EditingFragment());
//    fragmentTransaction.commit();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.help_menu, menu);
    return true;
  }

  private void setupNavigation() {
    navOptions = new NavOptions.Builder()
        .setPopUpTo(R.id.navigation_map, true)
        .build();
    navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController);
  }

  public void showToast(String message) {
    Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0,
        getResources().getDimensionPixelOffset(R.dimen.toast_vertical_margin));
    toast.show();
  }

  private void setupViewModel() {
    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    navigateTo(R.id.fragment_home);
    getLifecycle().addObserver(viewModel);
  }

  private void navigateTo(int itemId) {
    if (navController.getCurrentDestination().getId() != itemId) {
      navController.navigate(itemId, null, navOptions);
    }
  }
}
