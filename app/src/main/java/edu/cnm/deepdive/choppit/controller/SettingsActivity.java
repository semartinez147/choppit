package edu.cnm.deepdive.choppit.controller;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.choppit.R;

public class SettingsActivity extends AppCompatActivity implements
    SharedPreferences.OnSharedPreferenceChangeListener {

  private static Application context;
  private final SharedPreferences preferences;

  private SettingsActivity() {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

  }

  private String getFontPreference() {
    return preferences.getString(context.getString(R.string.sp_key_recipe_font_style), "Droid Sans");
  }


  public static class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setPreferencesFromResource(R.xml.settings, rootKey);
    }
  }


}
