package edu.cnm.deepdive.choppit.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.cnm.deepdive.choppit.R;
import javax.annotation.Nonnull;

public class IngredientListAdapter extends ArrayAdapter {

  private final Activity context;
  private final double[] measurement;
  private final String[] unit;
  private final String[] name;

  public IngredientListAdapter(Activity context,
      double[] measurement, String[] unit, String[] name) {
    super(context, R.layout.edit_ingredient_list_item, unit);
    this.context = context;
    this.measurement = measurement;
    this.unit = unit;
    this.name = name;
  }

  @Nonnull
  public View getView (int position, View view, @Nonnull ViewGroup parent) {
    LayoutInflater inflater = context.getLayoutInflater();
    View rowView = inflater.inflate(R.layout.edit_ingredient_list_item, null, true);

    // working from https://www.javacodegeeks.com/2013/09/android-viewholder-pattern-example.html

    TextView measurementField = (TextView) rowView.findViewById(R.id.measurement);
    TextView unitField = (TextView) rowView.findViewById(R.id.unit);
    TextView nameField = (TextView) rowView.findViewById(R.id.name);

    measurementField.setText(String.format("%.2f", measurement[position]));
    unitField.setText(unit[position]);
    nameField.setText(name[position]);

    return rowView;
  }

}