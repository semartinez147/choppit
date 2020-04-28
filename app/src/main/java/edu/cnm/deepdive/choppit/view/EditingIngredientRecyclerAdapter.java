package edu.cnm.deepdive.choppit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.choppit.databinding.EditIngredientItemBinding;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import java.util.ArrayList;
import java.util.List;

public class EditingIngredientRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final Context context;
  private final Recipe recipe;
  private final List<Ingredient> ingredients = new ArrayList<>();

  public EditingIngredientRecyclerAdapter(Context context,
      Recipe recipe) {
    this.context = context;
    this.recipe = recipe;
    for (Step step : recipe.getSteps()) {
      ingredients.addAll(step.getIngredients());
    }
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    EditIngredientItemBinding editIngredientItemBinding = EditIngredientItemBinding
        .inflate(layoutInflater, parent, false);
    return new IngredientViewHolder(editIngredientItemBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    Ingredient ingredient = ingredients.get(position);
    ((IngredientViewHolder) viewHolder).bind(ingredient);
  }

  @Override
  public int getItemCount() {
    return ingredients.size();
  }

  /**
   * The ViewHolder class coordinates between incoming data and the UI.  This handles {@link
   * Ingredient}s.
   */
  static class IngredientViewHolder extends RecyclerView.ViewHolder {

    private EditIngredientItemBinding binding;

    public IngredientViewHolder(EditIngredientItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    /**
     * This method attaches a specific {@link Ingredient} to a specific entry in the {@link
     * edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment}.
     *
     * @param ingredient the item to be bound.
     */
    public void bind(Ingredient ingredient) {
      binding.setVariable(edu.cnm.deepdive.choppit.BR.ingredient, ingredient);
      binding.executePendingBindings();
    }

  }
}
