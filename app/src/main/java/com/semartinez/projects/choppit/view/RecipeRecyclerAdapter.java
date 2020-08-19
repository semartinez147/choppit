package com.semartinez.projects.choppit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.semartinez.projects.choppit.databinding.RecipeIngredientItemBinding;
import com.semartinez.projects.choppit.databinding.RecipeStepItemBinding;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.LinkedList;
import java.util.List;

// FIXME complete data binding based on EditingRecyclerAdapter.

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

  final int VIEW_TYPE_INGREDIENT = 0;
  final int VIEW_TYPE_STEP = 1;

  private final Context context;
  private final List<Ingredient> ingredients;
  private final List<Step> steps;

  /**
   * Handles Data Binding input from the database and displays the contents of a {@link Recipe}.
   *
   * @param context the {@link Context} where the adapter operates.
   * @param recipe  the {@link Recipe} to be displayed
   */
  public RecipeRecyclerAdapter(Context context, Recipe recipe) {
    this.context = context;
    this.steps = recipe.getSteps();
    this.ingredients = new LinkedList<>();
    for (Step step : steps) {
      ingredients.addAll(step.getIngredients());
    }
  }

  @SuppressWarnings("ConstantConditions")
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_INGREDIENT) {
      LayoutInflater layoutInflater = LayoutInflater.from(context);
      RecipeIngredientItemBinding recipeIngredientItemBinding = RecipeIngredientItemBinding
          .inflate(layoutInflater, parent, false);
      return new IngredientViewHolder(recipeIngredientItemBinding);
    }
    if (viewType == VIEW_TYPE_STEP) {
      LayoutInflater layoutInflater = LayoutInflater.from(context);
      RecipeStepItemBinding recipeStepItemBinding = RecipeStepItemBinding
          .inflate(layoutInflater, parent, false);
      return new StepViewHolder(recipeStepItemBinding);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    if (viewHolder instanceof IngredientViewHolder) {
      ((IngredientViewHolder) viewHolder).bind(ingredients.get(position));
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
   * The ViewHolder class coordinates between incoming data and the UI.
   */
  static class IngredientViewHolder extends ViewHolder {

    private final RecipeIngredientItemBinding binding;

    private IngredientViewHolder(RecipeIngredientItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Ingredient ingredient) {
      binding.setIngredient(ingredient);
      binding.executePendingBindings();
    }
  }

  public static class StepViewHolder extends ViewHolder {


    private final RecipeStepItemBinding binding;

    private StepViewHolder(RecipeStepItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Step step) {
      binding.setStep(step);
      binding.executePendingBindings();
    }
  }
}