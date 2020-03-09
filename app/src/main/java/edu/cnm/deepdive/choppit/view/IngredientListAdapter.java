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
    Holder viewHolder;

    LayoutInflater inflater = context.getLayoutInflater();
    View rowView = inflater.inflate(R.layout.edit_ingredient_list_item, parent, true);

    viewHolder = new Holder(view);
    viewHolder.measurement = (TextView) rowView.findViewById(R.id.measurement);
    viewHolder.unit = (TextView) rowView.findViewById(R.id.unit);
    viewHolder.name = (TextView) rowView.findViewById(R.id.name);

    // working from https://www.javacodegeeks.com/2013/09/android-viewholder-pattern-example.html

//    measurementTextField.setText(String.format("%.2f", measurement[position]));
//    unitTextField.setText(unit[position]);
//    nameTextField.setText(name[position]);

    return rowView;
  }

   static class Holder {

    private final View view;
    private TextView measurement;
    private TextView unit;
    private TextView name;

    private Holder (@Nonnull View view) {
      super();
      this.view = view;
      measurement = view.findViewById(R.id.measurement);
      unit = view.findViewById(R.id.unit);
      name = view.findViewById(R.id.name);
    }

  }

}