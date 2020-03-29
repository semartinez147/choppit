package edu.cnm.deepdive.choppit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.choppit.controller.ui.cookbook.CookbookFragment;
import edu.cnm.deepdive.choppit.databinding.CookbookListItemBinding;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.view.CookbookRecyclerAdapter.ViewHolder;
import java.util.List;

public class CookbookRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final Context context;
  private final List<Recipe> recipes;
  private final OnRecipeClickListener onRecipeClickListener;

  /**
   * Handles Data Binding input from the database and displays a list of {@link Recipe}s
   *
   * @param context  the {@link Context} where the adapter operates.
   * @param recipes  the list of {@link Recipe}s to be displayed
   * @param listener handles onClick events.
   */
  public CookbookRecyclerAdapter(Context context, List<Recipe> recipes,
      OnRecipeClickListener listener) {
    this.context = context;
    this.recipes = recipes;
    this.onRecipeClickListener = listener;
  }

  private void updateRecipes(List<Recipe> newRecipes) {
    recipes.clear();
    recipes.addAll(newRecipes);
    notifyDataSetChanged();
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    CookbookListItemBinding cookbookListItemBinding = CookbookListItemBinding
        .inflate(layoutInflater, parent, false);
    return new ViewHolder(cookbookListItemBinding, onRecipeClickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Recipe recipe = recipes.get(position);
    holder.bind(recipe);
  }

  @Override
  public int getItemCount() {
    return recipes.size();
  }

  /**
   * The ViewHolder class coordinates between incoming data and the UI.
   */
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final View clickView;
    OnRecipeClickListener onRecipeClickListener;
    private CookbookListItemBinding binding;

    /**
     * The ViewHolder constructor attaches a {@link View} to each binding to support an
     * onClickListener.
     *
     * @param binding is the connection between the data and the interface.
     * @param onRecipeClickListener handles click events within the Recycler Adapter
     */
    public ViewHolder(CookbookListItemBinding binding,
        OnRecipeClickListener onRecipeClickListener) {
      super(binding.getRoot());
      this.binding = binding;
      this.onRecipeClickListener = onRecipeClickListener;
      clickView = binding.clickView;
      clickView.setOnClickListener(this);
    }

    /**
     * This method attaches a specific {@link Recipe} to a specific entry in the {@link
     * CookbookFragment}.  It attaches an
     * onClickListener that will be used to navigate into the Recipe screen.
     *
     * @param recipe is received from the database
     */
    public void bind(Recipe recipe) {
      binding.setVariable(edu.cnm.deepdive.choppit.BR.recipe, recipe);
      binding.executePendingBindings();
    }

    @Override
    public void onClick(View v) {
      onRecipeClickListener.onRecipeClick(getAdapterPosition());
    }
  }

  // TODO navigate to recipe via recipeID

  /**
   * Not implemented yet.
   */
  @FunctionalInterface
  public interface OnRecipeClickListener {

    void onRecipeClick(int position);
  }
}