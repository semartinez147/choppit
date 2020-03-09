package edu.cnm.deepdive.choppit.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.choppit.R;
import javax.annotation.Nonnull;

public class IngredientListAdapter extends ArrayAdapter {

  private final Activity context;
  private final Double[] measurement;
  private final String[] unit;
  private final String[] name;
  private View rowView;

  public IngredientListAdapter(Activity context,
      Double[] measurement, String[] unit, String[] name) {
    super(context, R.layout.edit_ingredient_list_item, unit);
    this.context = context;
    this.measurement = measurement;
    this.unit = unit;
    this.name = name;
      }

  @Nonnull
  public View getView(int position, View rowView, @Nonnull ViewGroup parent) {
    ViewHolder holder;
    View v = rowView;
    // working from https://www.javacodegeeks.com/2013/09/android-viewholder-pattern-example.html

    if (v == null) {
      LayoutInflater inflater = context.getLayoutInflater();
      v = inflater.inflate(R.layout.edit_ingredient_list_item, null, true);

      holder = new ViewHolder(v);
      holder.measurement = (TextView) v.findViewById(R.id.measurement);
      holder.unit = (TextView) v.findViewById(R.id.unit);
      holder.name = (TextView) v.findViewById(R.id.name);
      v.setTag(holder);
    } else {
      holder = (ViewHolder) v.getTag();
    }
    Double measurementItem = measurement[position];
    String nameItem = name[position];
    if (measurementItem != null) {
      holder.measurement.setText(String.format("%.2f", measurement[position]));
    }
    holder.unit.setText(unit[position]);
    if (nameItem != null) {
      holder.name.setText(name[position]);
    }
    return v;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView measurement;
    private TextView unit;
    private TextView name;

    private ViewHolder(@Nonnull View view) {
      super(view);
      measurement = view.findViewById(R.id.measurement);
      unit = view.findViewById(R.id.unit);
      name = view.findViewById(R.id.name);
    }

  }

}