package com.semartinez.projects.choppit.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.semartinez.projects.choppit.BR;
import com.semartinez.projects.choppit.controller.ui.editing.EditingFragment;
import com.semartinez.projects.choppit.databinding.EditIngredientItemBinding;
import com.semartinez.projects.choppit.databinding.EditStepItemBinding;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.ArrayList;
import java.util.List;

public class EditingRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  final int VIEW_TYPE_INGREDIENT = 0;
  final int VIEW_TYPE_STEP = 1;

  private final Context context;
  private final List<Ingredient> ingredients = new ArrayList<>();
  private final List<Step> steps;

  /**
   * Handles Data Binding input from the {@link com.semartinez.projects.choppit.viewmodel.MainViewModel}
   * and displays a list of {@link Ingredient}s and {@link Step}s.  The Recycler adapter determines
   * based on item position and the {@link List#size()} method whether it is displaying a {@link
   * Step} or {@link Ingredient} and inflates the appropriate binding layout.
   *
   * @param context the {@link Context} where the adapter operates.
   * @param recipe  is received from the {@link EditingFragment}.
   */
  public EditingRecyclerAdapter(Context context, Recipe recipe) {
    this.context = context;
    this.steps = recipe.getSteps();
    ingredients.addAll(recipe.getIngredients());
  }

  @SuppressWarnings("unused")
  private void updateIngredients(List<Ingredient> newIngredients) {
    ingredients.clear();
    ingredients.addAll(newIngredients);
    notifyDataSetChanged();
  }

  @SuppressWarnings("unused")
  private void updateSteps(List<Step> newSteps) {
    steps.clear();
    steps.addAll(newSteps);
    notifyDataSetChanged();
  }

  @SuppressWarnings("ConstantConditions")
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_INGREDIENT) {
      LayoutInflater layoutInflater = LayoutInflater.from(context);
      EditIngredientItemBinding editIngredientItemBinding = EditIngredientItemBinding
          .inflate(layoutInflater, parent, false);

      return new IngredientViewHolder(editIngredientItemBinding);
    }
    if (viewType == VIEW_TYPE_STEP) {
      LayoutInflater layoutInflater = LayoutInflater.from(context);
      EditStepItemBinding editStepItemBinding = EditStepItemBinding
          .inflate(layoutInflater, parent, false);
      return new StepViewHolder(editStepItemBinding);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    if (viewHolder instanceof IngredientViewHolder) {
      Ingredient ingredient = ingredients.get(position);
      ((IngredientViewHolder) viewHolder).bind(ingredient);
    }
    if (viewHolder instanceof StepViewHolder) {
      Step step = steps.get(position - ingredients.size());
      ((StepViewHolder) viewHolder).bind(step);
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
   * The ViewHolder class coordinates between incoming data and the UI.  This handles {@link
   * Ingredient}s.
   */
  static class IngredientViewHolder extends RecyclerView.ViewHolder {

    private final EditIngredientItemBinding binding;

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
    public void bind(Ingredient ingredient) {
      binding.setVariable(com.semartinez.projects.choppit.BR.ingredient, ingredient);
      binding.executePendingBindings();
    }

  }

  /**
   * The ViewHolder class coordinates between incoming data and the UI.  This handles {@link
   * Step}s.
   */
  public class StepViewHolder extends RecyclerView.ViewHolder {

    private final EditStepItemBinding binding;

    public StepViewHolder(EditStepItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    /**
     * This method attaches a specific {@link Step} to a specific entry in the {@link
     * com.semartinez.projects.choppit.controller.ui.editing.EditingFragment}.
     *
     * @param step the item to be bound
     */
    public void bind(Step step) {
      binding.setVariable(BR.step, step);
      step.setRecipeOrder(getAdapterPosition() - ingredients.size() + 1);
      Log.d("Bound step ", Integer.toString(getAdapterPosition() - ingredients.size()));
      binding.executePendingBindings();
    }
  }
}
