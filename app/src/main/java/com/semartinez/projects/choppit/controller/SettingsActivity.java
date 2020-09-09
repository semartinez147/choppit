package com.semartinez.projects.choppit.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import com.semartinez.projects.choppit.R;

// TODO write typeface-changing behavior
// TODO implement font size change
// Investigate using http://github.com/airbnb/paris.

public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    Button button = findViewById(R.id.button);
    button.setOnClickListener(v -> {
      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
      startActivity(intent);
    });

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
