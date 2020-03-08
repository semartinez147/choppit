package edu.cnm.deepdive.choppit.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.textfield.TextInputLayout;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;

public class MainActivity extends AppCompatActivity {

  private ImageView logo;
  private TextInputLayout urlInput;
  private Button cookbook;
  private NavController navController;
  private NavOptions navOptions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupNavigation();
    setupButtons();
//    logo = findViewById(R.id.logo);
//    urlInput = findViewById(R.id.url_input);
//    Button newRecipe = findViewById(R.id.new_recipe);
//    newRecipe.setOnClickListener(new View.OnClickListener(){
//        public void onClick(View v) {/*
//          Intent i = new Intent(getActivity(), EditingFragment.class);
//          startActivity(i);
//*/        }
//    });
//        cookbook = findViewById(R.id.my_cookbook);
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
  }

  private void setupButtons() {
    Button newRecipe = findViewById(R.id.new_recipe);
    newRecipe.setOnClickListener((v) -> {
      EditingFragment fragment = EditingFragment.createInstance();

    });
  }

  private void navigateTo(int itemId) {
    if (navController.getCurrentDestination().getId() != itemId) {
      navController.navigate(itemId, null, navOptions);
    }
  }
}
