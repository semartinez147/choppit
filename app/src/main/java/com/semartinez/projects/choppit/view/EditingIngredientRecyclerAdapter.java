package com.semartinez.projects.choppit.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.semartinez.projects.choppit.controller.ui.editing.EditingFragment;
import com.semartinez.projects.choppit.databinding.EditIngredientItemBinding;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.ArrayList;
import java.util.List;

public class EditingIngredientRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final Context context;
  private final Recipe recipe;
  private EditingFragment uiController;
  private final List<Ingredient> ingredients = new ArrayList<>();

  public EditingIngredientRecyclerAdapter(Context context,
      Recipe recipe, EditingFragment editingFragment) {
    this.context = context;
    this.uiController = editingFragment;
    this.recipe = recipe;
    for (Step step : recipe.getSteps()) {
      for (Ingredient ingredient : step.getIngredients()) {
        if (!ingredients.contains(ingredient)) {
          ingredients.add(ingredient);
        }
      }
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
    ((IngredientViewHolder) viewHolder).bind(ingredient, uiController);

  }

  @Override
  public int getItemCount() {
    return ingredients.size();
  }

  public void addIngredient() {
    Log.d("Add ingredient", "Position " + ingredients.size());
    ingredients.add(new Ingredient());
    notifyItemInserted(ingredients.size());
  }

  public void deleteIngredient(int position) {
    Log.d("Adapter delete", "Position " + position);
    ingredients.remove(position);
    notifyItemRemoved(position);
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
     * com.semartinez.projects.choppit.controller.ui.editing.EditingFragment}.
     *
     * @param ingredient the item to be bound.
     */
    public void bind(Ingredient ingredient, EditingFragment uiController) {
      binding.setVariable(com.semartinez.projects.choppit.BR.ingredient, ingredient);
      binding.setPosition(getAdapterPosition());
      binding.setUiController(uiController);
      binding.executePendingBindings();
    }

  }

}
