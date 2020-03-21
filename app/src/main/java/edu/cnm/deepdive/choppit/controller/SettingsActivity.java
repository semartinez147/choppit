package edu.cnm.deepdive.choppit.controller;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.choppit.R;

@RequiresApi(api = VERSION_CODES.O)
public class SettingsActivity extends AppCompatActivity implements
    SharedPreferences.OnSharedPreferenceChangeListener {

  private static Application context;
  private final SharedPreferences preferences;
  private TextView title;
  private TextView body;
  Typeface lato = getResources().getFont(R.font.lato);
  Typeface lora = getResources().getFont(R.font.lora);
  Typeface quicksand = getResources().getFont(R.font.quicksand);
  Typeface roboto = getResources().getFont(R.font.roboto_slab);
  Typeface sans = getResources().getFont(R.font.droid_sans);

  private SettingsActivity() {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    title = findViewById(R.id.settings_title);
    body = findViewById(R.id.settings_body);
  }


  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    String font = getFontPreference();
    switch (font) {
      case "lato":
        title.setTypeface(lato);
        body.setTypeface(lato);
        break;
      case "lora":
        title.setTypeface(lora);
        body.setTypeface(lora);
        break;
      case "quicksand":
        title.setTypeface(quicksand);
        body.setTypeface(quicksand);
        break;
      case "robto":
        title.setTypeface(roboto);
        body.setTypeface(roboto);
        break;
      case "droid sans":
      default:
        title.setTypeface(sans);
        body.setTypeface(sans);
        break;
    }
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
