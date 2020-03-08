package edu.cnm.deepdive.choppit.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.cnm.deepdive.choppit.R;

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

  public View getView (int position, View view, ViewGroup parent) {
    LayoutInflater inflater = context.getLayoutInflater();
    View rowView = inflater.inflate(R.layout.edit_ingredient_list_item, null, true);

    TextView measurementTextField = (TextView) rowView.findViewById(R.id.measurement);
    TextView unitTextField = (TextView) rowView.findViewById(R.id.unit);
    TextView nameTextField = (TextView) rowView.findViewById(R.id.name);

    measurementTextField.setText(String.format("%.2f", measurement[position]));
    unitTextField.setText(unit[position]);
    nameTextField.setText(name[position]);

    return rowView;
  }

}