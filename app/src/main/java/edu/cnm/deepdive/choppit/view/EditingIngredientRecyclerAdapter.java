package edu.cnm.deepdive.choppit.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;
import edu.cnm.deepdive.choppit.databinding.EditIngredientItemBinding;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
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
    ((IngredientViewHolder) viewHolder).bind(ingredient, uiController);

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
    public void bind(Ingredient ingredient, EditingFragment uiController) {
      binding.setVariable(edu.cnm.deepdive.choppit.BR.ingredient, ingredient);
      binding.setPosition(getAdapterPosition());
      binding.setUiController(uiController);
      binding.executePendingBindings();
    }

    public void delete() {

    }

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

}
