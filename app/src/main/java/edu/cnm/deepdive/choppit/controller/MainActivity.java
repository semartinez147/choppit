package edu.cnm.deepdive.choppit.controller;

import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;

public class MainActivity extends AppCompatActivity {

  private NavController navController;
  private NavOptions navOptions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // TODO bring this back when done debugging Ingredient adapter.
//    HomeFragment homeFragment = new HomeFragment();
//    homeFragment.setArguments(getIntent().getExtras());
//    FragmentManager fragManager = getSupportFragmentManager();
//    FragmentTransaction transaction = fragManager.beginTransaction();
//    transaction.add(R.id.container, homeFragment);
//    transaction.commit();

    EditingFragment editingFragment = new EditingFragment();
    editingFragment.setArguments(getIntent().getExtras());
    FragmentManager fragManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragManager.beginTransaction();
    transaction.add(R.id.container, editingFragment);
    transaction.commit();
    
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.help_menu, menu);
    return true;
  }


}
