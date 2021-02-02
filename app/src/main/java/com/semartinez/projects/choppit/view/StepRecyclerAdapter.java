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
import com.semartinez.projects.choppit.databinding.ItemEditStepBinding;
import com.semartinez.projects.choppit.databinding.ItemRecipeStepBinding;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.List;

public class StepRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final Context context;
  private final EditingFragment editingController;
  private final RecipeFragment recipeController;
  private final boolean edit;
  private final List<Step> steps;

  public StepRecyclerAdapter(Context context,
      Recipe recipe, Fragment uiFragment) {
    this.context = context;
    this.edit = uiFragment instanceof EditingFragment;
    this.editingController = uiFragment instanceof EditingFragment ? (EditingFragment) uiFragment: null;
    this.recipeController = uiFragment instanceof RecipeFragment ? (RecipeFragment) uiFragment : null;
    this.steps = recipe.getSteps();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    if (edit) {
      ItemEditStepBinding editStepBinding = ItemEditStepBinding
          .inflate(layoutInflater, parent, false);
      Log.d("StepOnCreateViewHolder", "done");
      return new EditStepViewHolder(editStepBinding);
    } else {
      ItemRecipeStepBinding recipeStepBinding = ItemRecipeStepBinding
          .inflate(layoutInflater, parent, false);
      Log.d("StepOnCreateViewHolder", "done");
      return new RecipeStepViewHolder(recipeStepBinding);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    Step step = steps.get(position);
    if (edit) {
      ((EditStepViewHolder) viewHolder).bind(step);
    } else {
      ((RecipeStepViewHolder) viewHolder).bind(step);
    }
    Log.d("StepOnBindViewHolder", "bound" + position);
  }

  @Override
  public int getItemCount() {
    return steps.size();
  }

  public void addStep() {
    Log.d("addStep", "Add Step Button");
    steps.add(new Step());
    notifyItemInserted(steps.size());
  }

  // TODO: only deletes the first step
  public void deleteStep(int position) {
    steps.remove(position);
    notifyDataSetChanged();
  }

  public List<Step> getSteps() {
    return steps;
  }

  /**
   * The ViewHolder class coordinates between incoming data and the UI.  This handles {@link
   * Step}s.
   */
  public class EditStepViewHolder extends RecyclerView.ViewHolder {

    private final ItemEditStepBinding binding;

    public EditStepViewHolder(ItemEditStepBinding binding) {
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
      binding.setPosition(getAdapterPosition());
      binding.setUiController(editingController);
      step.setRecipeOrder(getAdapterPosition() + 1);
      Log.d("StepBind",
          "Adapter Position:" + getAdapterPosition() + " ... Step number " + step.getRecipeOrder()
              + " ... " + step.getInstructions());
      binding.executePendingBindings();
    }
  }

  public class RecipeStepViewHolder extends RecyclerView.ViewHolder {

    private final ItemRecipeStepBinding binding;

    public RecipeStepViewHolder(ItemRecipeStepBinding binding) {
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
      binding.setUiController(recipeController);

      Log.d("StepBind",
          "Adapter Position:" + getAdapterPosition() + " ... Step number " + step.getRecipeOrder()
              + " ... " + step.getInstructions());
      binding.executePendingBindings();
    }
  }

}
