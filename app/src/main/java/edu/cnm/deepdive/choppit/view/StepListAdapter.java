package edu.cnm.deepdive.choppit.view;

import android.annotation.SuppressLint;
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

public class StepListAdapter extends ArrayAdapter {

  private final Activity context;
  private final List<String> step;
  private View view;

  public StepListAdapter(Activity context, List<String> step) {
    super(context, R.layout.edit_ingredient_item, step);
    this.context = context;
    this.step = step;
      }

  @SuppressLint("SetTextI18n")
  @Nonnull
  public View getView(int position, View rowView, @Nonnull ViewGroup parent) {
    ViewHolder holder;
    view = rowView;
    // working from https://www.javacodegeeks.com/2013/09/android-viewholder-pattern-example.html

    if (view == null) {
      LayoutInflater inflater = context.getLayoutInflater();
      view = inflater.inflate(R.layout.edit_step_item, null, true);

      holder = new ViewHolder(view);
      holder.number = (TextView) view.findViewById(R.id.step_number);
      holder.step = (TextView) view.findViewById(R.id.step_input);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    String stepItem = step.get(position);
    if (stepItem != null) {
      holder.number.setText(Integer.toString(position + 1));
      holder.step.setText(step.get(position));
    }
    return view;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView number;
    private TextView step;

    private ViewHolder(@Nonnull View view) {
      super(view);
      number = view.findViewById(R.id.step_number);
      step = view.findViewById(R.id.step_input);
    }

  }

}