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
import com.semartinez.projects.choppit.databinding.EditStepItemBinding;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.List;

public class EditingStepRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final Context context;
  private final Recipe recipe;
  private EditingFragment uiController;
  private final List<Step> steps;

  public EditingStepRecyclerAdapter(Context context,
      Recipe recipe, EditingFragment editingFragment) {
    this.context = context;
    this.uiController = editingFragment;
    this.recipe = recipe;
    this.steps = recipe.getSteps();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    EditStepItemBinding editStepItemBinding = EditStepItemBinding
        .inflate(layoutInflater, parent, false);
    Log.d("StepOnCreateViewHolder", "done");
    return new StepViewHolder(editStepItemBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    Step step = steps.get(position);
    ((StepViewHolder) viewHolder).bind(step);
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

  public void deleteStep(int position) {
    steps.remove(position);
    notifyDataSetChanged();
    }


  /**
   * The ViewHolder class coordinates between incoming data and the UI.  This handles {@link
   * Step}s.
   */
  public class StepViewHolder extends RecyclerView.ViewHolder {

    private EditStepItemBinding binding;

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
      binding.setUiController(uiController);
      step.setRecipeOrder(getAdapterPosition() + 1);
      Log.d("StepBind", "Adapter Position:" + getAdapterPosition() + " ... Step number " + step.getRecipeOrder() + " ... " + step.getInstructions());
      binding.executePendingBindings();
    }
  }

}
