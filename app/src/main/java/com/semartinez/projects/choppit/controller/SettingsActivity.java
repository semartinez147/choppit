package com.semartinez.projects.choppit.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.ui.SettingsFragment;

// TODO write typeface-changing behavior
// TODO implement font size change
// Investigate using http://github.com/airbnb/paris.

public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, new SettingsFragment())
          .commitNow();

    }
  }


  /**
   * This {@link androidx.fragment.app.Fragment} is currently decorative. It will allow the user to
   * set the {@link android.graphics.Typeface} and text size to make reading the {@link
   * com.semartinez.projects.choppit.controller.ui.cookbook.RecipeFragment} easier.
   */
  public static class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setPreferencesFromResource(R.xml.settings, rootKey);
    }
  }
}
