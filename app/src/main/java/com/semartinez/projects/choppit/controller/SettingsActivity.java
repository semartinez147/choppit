package com.semartinez.projects.choppit.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.semartinez.projects.choppit.R;

// TODO write typeface-changing behavior
// TODO implement font size change

public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
  }

  /**
   * This {@link androidx.fragment.app.Fragment} is currently decorative. It will allow the user to
   * set the {@link android.graphics.Typeface} and text size to make reading the {@link
   * com.semartinez.projects.choppit.controller.ui.cookbook.RecipeFragment} easier.
   */
  public static class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setPreferencesFromResource(R.xml.settings, rootKey);
    }
  }
}
