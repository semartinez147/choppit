package com.semartinez.projects.choppit.model.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;
import androidx.preference.PreferenceManager;
import com.airbnb.paris.Paris;
import com.airbnb.paris.styles.Style;
import com.semartinez.projects.choppit.R;

public class StyleRepository implements SharedPreferences.OnSharedPreferenceChangeListener {

  private static Application context;
  private final SharedPreferences preferences;
  private static int fontId;
  private static int fontSize;

  public static void setContext(Application context) {
    StyleRepository.context = context;
  }


  public static StyleRepository getInstance() {
    return StyleRepository.InstanceHolder.INSTANCE;
  }

  private StyleRepository() {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.registerOnSharedPreferenceChangeListener(this);
    fontId =
    fontSize = preferences.getInt(context.getString(R.string.sp_key_recipe_font_size), 14);
  }

  public Style bodyStyleFromPref() {
    String typeface = preferences
        .getString(context.getString(R.string.sp_key_recipe_font_style), "Droid Sans");
    int fontSize = preferences.getInt(context.getString(R.string.sp_key_recipe_font_size), 14);

    return Paris.styleBuilder(new TextView(context))
        .textStyle(getTypeface(typeface))
        .textSize(fontSize).build();
  }

  public Style titleStyleFromPref() {
    String typeface = preferences
        .getString(context.getString(R.string.sp_key_recipe_font_style), "Droid Sans");
    int fontSize = preferences.getInt(context.getString(R.string.sp_key_recipe_font_size), 14)+6;

    Style style = Paris.styleBuilder(new TextView(context))
        .textStyle(getTypeface(typeface))
        .textSize(fontSize).build();

    return style;
  }

  private static int getTypeface(String typeface) {
    int typefaceId = R.style.Droid;
    switch (typeface) {
      case "Lato":
        typefaceId = R.style.Lato;
        break;
      case "Lora":
        typefaceId = R.style.Lora;
        break;
      case "Quicksand":
        typefaceId = R.style.Quicksand;
        break;
      case "Roboto Slab":
        typefaceId = R.style.Roboto;
        break;
      case "Droid Sans":
      default:
        typefaceId = R.style.Droid;
        break;
    }
    return typefaceId;
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    fontId = getTypeface(preferences
        .getString(context.getString(R.string.sp_key_recipe_font_style), "Droid Sans"));
    fontSize = preferences.getInt(context.getString(R.string.sp_key_recipe_font_size), 14);

  }

  @BindingAdapter("android:textSize")
  public static float textSize(TextView view, int size) {
    boolean title = view.getContentDescription().equals(context.getString(R.string.default_recipe_title));
    return (float) (title ? fontSize+6 : fontSize);
  }

  @BindingAdapter("font")
  public static void setFont(TextView view, String font) {
    Typeface typeface = ResourcesCompat.getFont(context, fontId);
    view.setTypeface(typeface);
  }

  private static class InstanceHolder {

    private static final StyleRepository INSTANCE = new StyleRepository();
  }
}
