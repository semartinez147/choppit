package edu.cnm.deepdive.choppit.controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.textfield.TextInputLayout;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase;

public class MainActivity extends AppCompatActivity {

  private ImageView logo;
  private TextInputLayout urlInput;
  private Button newRecipe;
  private Button cookbook;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    logo = findViewById(R.id.logo);
    urlInput = findViewById(R.id.url_input);
    newRecipe = findViewById(R.id.new_recipe);
    cookbook = findViewById(R.id.my_cookbook);

  }
}
