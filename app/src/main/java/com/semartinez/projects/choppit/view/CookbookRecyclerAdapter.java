package com.semartinez.projects.choppit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.semartinez.projects.choppit.controller.ui.cookbook.CookbookFragment;
import com.semartinez.projects.choppit.controller.ui.cookbook.CookbookFragmentDirections;
import com.semartinez.projects.choppit.databinding.CookbookListItemBinding;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.view.CookbookRecyclerAdapter.ViewHolder;
import java.util.List;

public class CookbookRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final Context context;
  private final List<Recipe> recipes;

  /**
   * Handles Data Binding input from the database and displays a list of {@link Recipe}s
   *
   * @param context  the {@link Context} where the adapter operates.
   * @param recipes  the list of {@link Recipe}s to be displayed
   */
  public CookbookRecyclerAdapter(Context context, List<Recipe> recipes) {
    this.context = context;
    this.recipes = recipes;
  }

  @SuppressWarnings("unused")
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
    return new ViewHolder(cookbookListItemBinding);
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
  public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final CookbookListItemBinding binding;

    /**
     * The ViewHolder constructor attaches a {@link View} to each binding to support an
     * onClickListener.
     *
     * @param binding is the connection between the data and the interface.
     */
    public ViewHolder(CookbookListItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      binding.recipeTitle.setOnClickListener(this);
    }

    /**
     * This method attaches a specific {@link Recipe} to a specific entry in the {@link
     * CookbookFragment}.  It attaches an
     * onClickListener that will be used to navigate into the Recipe screen.
     *
     * @param recipe is received from the database
     */
    public void bind(Recipe recipe) {
      binding.setVariable(com.semartinez.projects.choppit.BR.recipe, recipe);
      binding.executePendingBindings();
    }

    @Override
    public void onClick(View v) {
      CookbookFragmentDirections.CookRec action = CookbookFragmentDirections.cookRec();
      action.setRecipeId(binding.getRecipe().getId());
      Navigation.findNavController(v).navigate(action);

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