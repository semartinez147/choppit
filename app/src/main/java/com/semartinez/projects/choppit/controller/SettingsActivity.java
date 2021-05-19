package com.semartinez.projects.choppit.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.model.repository.RecipeRepository;

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
  public static class SettingsFragment extends PreferenceFragmentCompat {

    private RecipeRepository repository;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
      repository = RecipeRepository.getInstance();
      super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setPreferencesFromResource(R.xml.settings, rootKey);
    }

    // TODO: figure out how to change font & size through PreviewPreference &/or preview_layout.

  }

  public static class PreviewPreference extends EditTextPreference {

    //<editor-fold desc="Constructors">
    public PreviewPreference(Context context, AttributeSet attrs,
        int defStyleAttr, int defStyleRes) {
      super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PreviewPreference(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
    }

    public PreviewPreference(Context context, AttributeSet attrs) {
      super(context, attrs);
    }

    public PreviewPreference(Context context) {
      super(context);
    }
    //</editor-fold>

    @Override
    public int getDialogLayoutResource() {
      return R.layout.preview_layout;
    }


  }
}
