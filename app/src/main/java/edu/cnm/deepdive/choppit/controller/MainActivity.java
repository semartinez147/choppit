package edu.cnm.deepdive.choppit.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

  HomeFragment homeFragment = new HomeFragment();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    homeFragment.setArguments(getIntent().getExtras());
//    HomeFragment homeFragment = new HomeFragment();
//    homeFragment.setArguments(getIntent().getExtras());
//    FragmentManager fragManager = getSupportFragmentManager();
//    FragmentTransaction transaction = fragManager.beginTransaction();
//    transaction.add(R.id.container, homeFragment, "homeFragment");
//    transaction.commit();

    // use to test other fragments
    getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.nav_host_fragment, homeFragment)
        .commit();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.help:
        //TODO help options
        break;

      case R.id.settings:
        Intent intent = new Intent(this, SettingsActivity.class);
    }


    return handled;
  }
}
