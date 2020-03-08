package edu.cnm.deepdive.choppit.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.textfield.TextInputLayout;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;
import edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

  private NavController navController;
  private NavOptions navOptions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    HomeFragment homeFragment = new HomeFragment();
    homeFragment.setArguments(getIntent().getExtras());
    FragmentManager fragManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragManager.beginTransaction();
    transaction.add(R.id.container, homeFragment);
    transaction.commit();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.help_menu, menu);
    return true;
  }


}
