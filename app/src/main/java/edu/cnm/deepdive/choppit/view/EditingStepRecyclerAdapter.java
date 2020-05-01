package edu.cnm.deepdive.choppit.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.choppit.BR;
import edu.cnm.deepdive.choppit.databinding.EditStepItemBinding;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import java.util.List;

public class EditingStepRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final Context context;
  private final Recipe recipe;
  private final List<Step> steps;

  public EditingStepRecyclerAdapter(Context context,
      Recipe recipe) {
    this.context = context;
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
     * edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment}.
     *
     * @param step the item to be bound
     */
    public void bind(Step step) {
      binding.setVariable(BR.step, step);
      step.setRecipeOrder(getAdapterPosition() + 1);
      Log.d("StepBind", "Adapter Position:" + getAdapterPosition() + " ... Step number " + step.getRecipeOrder() + " ... " + step.getInstructions());
      binding.executePendingBindings();
    }
  }

}
