package edu.cnm.deepdive.choppit.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;
import edu.cnm.deepdive.choppit.databinding.EditIngredientItemBinding;
import edu.cnm.deepdive.choppit.databinding.EditStepItemBinding;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Step;
import java.util.List;

public class EditingRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  final int VIEW_TYPE_INGREDIENT = 0;
  final int VIEW_TYPE_STEP = 1;

  private final Context context;
  private List<Ingredient> ingredients;
  private List<Step> steps;
  EditingFragment editingFragment;

  public EditingRecyclerAdapter(Context context, List<Ingredient> ingredients, List<Step> steps) {
    this.context = context;
    this.steps = steps;
    this.ingredients = ingredients;
    editingFragment = new EditingFragment();
  }

  public void updateIngredients(List<Ingredient> newIngredients) {
    ingredients.clear();
    ingredients.addAll(newIngredients);
    notifyDataSetChanged();
  }

  public void updateSteps(List<Step> newSteps) {
    steps.clear();
    steps.addAll(newSteps);
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_INGREDIENT) {
      LayoutInflater layoutInflater = LayoutInflater.from(context);
      EditIngredientItemBinding editIngredientItemBinding = EditIngredientItemBinding.inflate(layoutInflater, parent, false);
      return new IngredientViewHolder(editIngredientItemBinding);
    }
    if (viewType == VIEW_TYPE_STEP) {
      LayoutInflater layoutInflater = LayoutInflater.from(context);
      EditStepItemBinding editStepItemBinding = EditStepItemBinding.inflate(layoutInflater, parent, false);
      return new StepViewHolder(editStepItemBinding);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    if (viewHolder instanceof IngredientViewHolder) {
      Ingredient ingredient = ingredients.get(position);
      ((IngredientViewHolder) viewHolder).bind(ingredient);
      Log.d(Integer.toString(position), ingredient.getName());
    }
    if (viewHolder instanceof StepViewHolder) {
      Step step = steps.get(position);
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

  public class IngredientViewHolder extends RecyclerView.ViewHolder {

    private TextView quantity;
    private TextView unit;
    private TextView name;
    private EditIngredientItemBinding binding;

    public IngredientViewHolder(EditIngredientItemBinding binding) {
      super(binding.getRoot());

      this.binding = binding;

//      quantity = itemView.findViewById(R.id.editing_quantity);
//      unit = itemView.findViewById(R.id.editing_unit);
//      name = itemView.findViewById(R.id.editing_name);
    }

    public void bind(Ingredient ingredient) {
      binding.setIngredient(ingredient);
      binding.executePendingBindings();
    }
/*
    public void populate(Ingredient ingredient) {
      quantity.setText(ingredient.getQuantity());
      unit.setText(ingredient.getUnit().toString());
      name.setText(ingredient.getName());
    }
*/
  }

  public class StepViewHolder extends RecyclerView.ViewHolder {

    private TextView order;
    private TextView step;
    private EditStepItemBinding binding;

    public StepViewHolder(EditStepItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Step step) {
      binding.setStep(step);
      step.setRecipeOrder(getAdapterPosition() - ingredients.size());
      binding.executePendingBindings();
    }
/*
    public void populate(Step step) {
      order.setText(Integer.toString(getAdapterPosition() + 1));
      this.step.setText(step.getInstructions());
    }
*/
  }
}
