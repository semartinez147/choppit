package edu.cnm.deepdive.choppit.view;

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
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;

// FIXME complete data binding
// FIXME handle incoming navigation

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

  final int VIEW_TYPE_INGREDIENT = 0;
  final int VIEW_TYPE_STEP = 1;

  private final Context context;
  private final Recipe recipe;
  private final List<Ingredient> ingredients;
  private final List<Step> steps;

  /**
   * Handles Data Binding input from the database and displays the contents of a {@link Recipe}.
   *
   * @param context  the {@link Context} where the adapter operates.
   * @param recipe  the {@link Recipe} to be displayed
   */
  public RecipeRecyclerAdapter(Context context, Recipe recipe) {
    this.context = context;
    this.recipe = recipe;
    this.steps = this.recipe.getSteps();
    this.ingredients = new LinkedList<>();
    for (Step step : steps) {
      ingredients.addAll(step.getIngredients());
    }
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_INGREDIENT) {
      View view = LayoutInflater.from(context)
          .inflate(R.layout.recipe_ingredient_item, null, true);

      return new IngredientViewHolder(view);
    }
    if (viewType == VIEW_TYPE_STEP) {
      View view = LayoutInflater.from(context).inflate(R.layout.recipe_step_item, null, true);
      return new StepViewHolder(view);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    if (viewHolder instanceof IngredientViewHolder) {
      ((IngredientViewHolder) viewHolder).bind(ingredients.get(position));
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
  /**
   * The ViewHolder class coordinates between incoming data and the UI.
   */
  static class IngredientViewHolder extends ViewHolder {

    private TextView quantity;
    private TextView unit;
    private TextView name;

    private IngredientViewHolder(@Nonnull View itemView) {
      super(itemView);
      quantity = itemView.findViewById(R.id.recipe_quantity);
      unit = itemView.findViewById(R.id.recipe_unit);
      name = itemView.findViewById(R.id.recipe_name);
    }

    public void bind(Ingredient ingredient) {
      quantity.setText(ingredient.getQuantity());
      unit.setText(ingredient.getUnit().toString());
      name.setText(ingredient.getName());
    }
  }

  public static class StepViewHolder extends ViewHolder {

    private TextView order;
    private TextView step;

    private StepViewHolder(@Nonnull View itemView) {
      super(itemView);
      order = itemView.findViewById(R.id.recipe_step_number);
      step = itemView.findViewById(R.id.recipe_step);
    }

    public void bind(Step step) {
      order.setText(step.getRecipeOrder());
      this.step.setText(step.getInstructions());
    }
  }
}
