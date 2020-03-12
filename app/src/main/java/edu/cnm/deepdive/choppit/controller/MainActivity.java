package edu.cnm.deepdive.choppit.controller;

import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.cookbook.CookbookFragment;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;
import edu.cnm.deepdive.choppit.controller.ui.editing.SelectionFragment;
import edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    HomeFragment homeFragment = new HomeFragment();
    homeFragment.setArguments(getIntent().getExtras());
    FragmentManager fragManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragManager.beginTransaction();
    transaction.add(R.id.container, homeFragment, "homeFragment");
    transaction.commit();

    // use to test other fragments
//    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//    fragmentTransaction.replace(R.id.container, new EditingFragment());
//    fragmentTransaction.commit();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.help_menu, menu);
    return true;
  }


}
