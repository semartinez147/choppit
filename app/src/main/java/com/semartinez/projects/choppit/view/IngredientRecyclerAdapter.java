package com.semartinez.projects.choppit.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.semartinez.projects.choppit.BR;
import com.semartinez.projects.choppit.controller.ui.cookbook.RecipeFragment;
import com.semartinez.projects.choppit.controller.ui.editing.EditingFragment;
import com.semartinez.projects.choppit.databinding.EditIngredientItemBinding;
import com.semartinez.projects.choppit.databinding.RecipeIngredientItemBinding;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.ArrayList;
import java.util.List;

public class IngredientRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final Context context;
  private final EditingFragment editingController;
  private final RecipeFragment recipeController;
  private final boolean edit;
  private final List<Ingredient> ingredients = new ArrayList<>();

  public IngredientRecyclerAdapter (Context context, Recipe recipe, Fragment uiFragment) {
    this.context = context;
    this.edit = uiFragment instanceof EditingFragment;
    this.editingController = uiFragment instanceof EditingFragment ? (EditingFragment) uiFragment: null;
    this.recipeController = uiFragment instanceof RecipeFragment ? (RecipeFragment) uiFragment : null;
    this.ingredients.addAll(recipe.getIngredients());
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    if (edit) {
      EditIngredientItemBinding editIngredientItemBinding = EditIngredientItemBinding
          .inflate(layoutInflater, parent, false);
      return new EditIngredientViewHolder(editIngredientItemBinding);
    } else {
      RecipeIngredientItemBinding recipeIngredientItemBinding = RecipeIngredientItemBinding
          .inflate(layoutInflater, parent, false);
      return new RecipeIngredientViewHolder(recipeIngredientItemBinding);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    Ingredient ingredient = ingredients.get(position);
    if (edit) {
      ((EditIngredientViewHolder) viewHolder).bind(ingredient);
    } else {
      ((RecipeIngredientViewHolder) viewHolder).bind(ingredient);
    }
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
  public class EditIngredientViewHolder extends RecyclerView.ViewHolder {

    private final EditIngredientItemBinding binding;

    public EditIngredientViewHolder(EditIngredientItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    /**
     * This method attaches a specific {@link Ingredient} to a specific entry in the {@link
     * com.semartinez.projects.choppit.controller.ui.editing.EditingFragment}.
     *
     * @param ingredient the item to be bound.
     */
    public void bind(Ingredient ingredient) {
      binding.setVariable(com.semartinez.projects.choppit.BR.ingredient, ingredient);
      binding.setPosition(getAdapterPosition());
      binding.setUiController(editingController);
      binding.executePendingBindings();
    }

  }

public class RecipeIngredientViewHolder extends RecyclerView.ViewHolder {

    private final RecipeIngredientItemBinding binding;

    public RecipeIngredientViewHolder(RecipeIngredientItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    /**
     * This method attaches a specific {@link Ingredient} to a specific entry in the {@link
     * com.semartinez.projects.choppit.controller.ui.editing.EditingFragment}.
     *
     * @param ingredient the item to be bound.
     */
    public void bind(Ingredient ingredient) {
      binding.setVariable(BR.ingredient, ingredient);
      binding.setUiController(recipeController);
      binding.executePendingBindings();
    }

  }

}
