package com.semartinez.projects.choppit.view;

import android.content.Context;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.ui.cookbook.CookbookFragment;
import com.semartinez.projects.choppit.controller.ui.cookbook.CookbookFragmentDirections;
import com.semartinez.projects.choppit.databinding.ItemCookbookListBinding;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.view.CookbookRecyclerAdapter.CookbookViewHolder;
import java.util.List;

public class CookbookRecyclerAdapter extends RecyclerView.Adapter<CookbookViewHolder> {

  private final Context context;
  private final List<Recipe> recipes;
  private final CookbookFragment cookbookFragment;

  /**
   * Handles Data Binding input from the database and displays a list of {@link Recipe}s
   *  @param context the {@link Context} where the adapter operates.
   * @param recipes the list of {@link Recipe}s to be displayed
   * @param cookbookFragment the active Fragment
   */
  public CookbookRecyclerAdapter(Context context, List<Recipe> recipes,
      CookbookFragment cookbookFragment) {
    this.context = context;
    this.recipes = recipes;
    this.cookbookFragment = cookbookFragment;
  }

  @SuppressWarnings("unused")
  private void updateRecipes(List<Recipe> newRecipes) {
    recipes.clear();
    recipes.addAll(newRecipes);
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public CookbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    ItemCookbookListBinding itemCookbookListBinding = ItemCookbookListBinding
        .inflate(layoutInflater, parent, false);
    return new CookbookViewHolder(itemCookbookListBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull CookbookViewHolder holder, int position) {
    Recipe recipe = recipes.get(position);
    holder.bind(recipe, cookbookFragment);
  }

  @Override
  public int getItemCount() {
    return recipes.size();
  }

  /**
   * The ViewHolder class coordinates between incoming data and the UI.
   */
  static class CookbookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ItemCookbookListBinding binding;

    /**
     * The ViewHolder constructor attaches a {@link View} to each binding to support an
     * onClickListener.
     *
     * @param binding is the connection between the data and the interface.
     */
    public CookbookViewHolder(ItemCookbookListBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      binding.recipeTitle.setOnClickListener(this);
      binding.recipeFavorite.setOnClickListener(this);
      binding.edit.setOnClickListener(this);

      binding.recipeTitle.setOnLongClickListener(v -> {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
        binding.getUiController().deleteRecipe(binding.getRecipe());
        return false;
      });
    }

    /**
     * This method attaches a specific {@link Recipe} to a specific entry in the {@link
     * CookbookFragment}.  It attaches an onClickListener that will be used to navigate into the
     * Recipe screen.
     *
     * @param recipe is received from the database
     */
    public void bind(Recipe recipe, CookbookFragment cookbookFragment) {
      binding.setUiController(cookbookFragment);
      binding.setVariable(com.semartinez.projects.choppit.BR.recipe, recipe);
      binding.executePendingBindings();
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.recipe_title:
          CookbookFragmentDirections.CookRec action = CookbookFragmentDirections.cookRec();
          action.setRecipeId(binding.getRecipe().getRecipeId());
          Navigation.findNavController(v).navigate(action);
          break;
        case R.id.recipe_favorite:
          binding.getRecipe().setFavorite(!binding.getRecipe().isFavorite());
          break;
        case R.id.edit:
          CookbookFragmentDirections.CookEdit toEdit = CookbookFragmentDirections.cookEdit();
          toEdit.setRecipeId(binding.getRecipe().getRecipeId());
          Navigation.findNavController(v).navigate(toEdit);
          break;
        default:
          break;
      }
    }
  }

}