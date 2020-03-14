package edu.cnm.deepdive.choppit.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.choppit.R;
import java.util.List;
import javax.annotation.Nonnull;

// TODO Switch to Recycler View
public class IngredientListAdapter extends ArrayAdapter {

  private final Activity context;
  private final List<String> measurement;
  private final List<String> unit;
  private final List<String> name;
  private View view;

  public IngredientListAdapter(Activity context,
      List<String> measurement, List<String> unit, List<String> name) {
    super(context, R.layout.edit_ingredient_list_item, unit);
    this.context = context;
    this.measurement = measurement;
    this.unit = unit;
    this.name = name;
      }

  @Nonnull
  public View getView(int position, View rowView, @Nonnull ViewGroup parent) {
    ViewHolder holder;
    view = rowView;
    // working from https://www.javacodegeeks.com/2013/09/android-viewholder-pattern-example.html

    if (view == null) {
      LayoutInflater inflater = context.getLayoutInflater();
      view = inflater.inflate(R.layout.edit_ingredient_list_item, null, true);

      holder = new ViewHolder(view);
      holder.measurement = (TextView) view.findViewById(R.id.measurement);
      holder.unit = (TextView) view.findViewById(R.id.unit);
      holder.name = (TextView) view.findViewById(R.id.name);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    String measurementItem = measurement.get(position);
    String nameItem = name.get(position);
    if (measurementItem != null) {
      holder.measurement.setText(String.format("%.2f", measurement.get(position)));
    }
    holder.unit.setText(unit.get(position));
    if (nameItem != null) {
      holder.name.setText(name.get(position));
    }
    return view;
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