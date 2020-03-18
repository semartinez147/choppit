package edu.cnm.deepdive.choppit.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Step;
import java.util.List;
import javax.annotation.Nonnull;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  final int VIEW_TYPE_INGREDIENT = 0;
  final int VIEW_TYPE_STEP = 1;

  private final Context context;
  private final List<Ingredient> ingredients;
  private final List<Step> steps;

  public RecipeRecyclerAdapter(Context context, List<Ingredient> ingredients, List<Step> steps) {
    this.context = context;
    this.steps = steps;
    this.ingredients = ingredients;
//    TODO delete this, probably.
//    for (StepWithDetails step : recipe.getStepWithDetails()) {
//      this.ingredients.addAll(step.getIngredients());
//    }

  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_INGREDIENT) {
      View view = LayoutInflater.from(context)
          .inflate(R.layout.edit_ingredient_list_item, null, true);

      return new IngredientViewHolder(view);
    }
    if (viewType == VIEW_TYPE_STEP) {
      View view = LayoutInflater.from(context).inflate(R.layout.edit_step_list_item, null, true);
      return new StepViewHolder(view);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    if (viewHolder instanceof IngredientViewHolder) {
      ((IngredientViewHolder) viewHolder).populate(ingredients.get(position));
    }
  }

  @Override
  public int getItemCount() {
    return ingredients.size() + steps.size();
  }

  @Override
  public int getItemViewType(int position) {
    if (position < ingredients.size()) {
      return VIEW_TYPE_INGREDIENT;
    }
    if (position - ingredients.size() < steps.size()) {
      return VIEW_TYPE_STEP;
    }
    return -1;
  }

  public class IngredientViewHolder extends RecyclerView.ViewHolder {

    private TextView quantity;
    private TextView unit;
    private TextView name;

    private IngredientViewHolder(@Nonnull View itemView) {
      super(itemView);
      quantity = itemView.findViewById(R.id.quantity);
      unit = itemView.findViewById(R.id.unit);
      name = itemView.findViewById(R.id.name);
    }

    public void populate(Ingredient ingredient) {
      quantity.setText(ingredient.getQuantity()); // FIXME change all Quantities to Strings
      unit.setText(ingredient.getUnit().toString());
      name.setText(ingredient.getName());
    }
  }

  public class StepViewHolder extends RecyclerView.ViewHolder {

    private TextView quantity;
    private TextView step;

    private StepViewHolder(@Nonnull View itemView) {
      super(itemView);
      quantity = itemView.findViewById(R.id.step_number);
      step = itemView.findViewById(R.id.step_input);
    }

    public void populate(Step step) {
      quantity.setText(Integer.toString(getAdapterPosition() + 1));
      this.step.setText(step.getInstructions());
    }
  }
}
