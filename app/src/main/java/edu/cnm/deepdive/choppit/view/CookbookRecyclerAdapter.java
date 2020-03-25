package edu.cnm.deepdive.choppit.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.choppit.BR;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.databinding.CookbookListItemBinding;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.view.CookbookRecyclerAdapter.ViewHolder;
import java.util.List;
import javax.annotation.Nonnull;

public class CookbookRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final Context context;
  private final List<Recipe> recipes;
  private final OnRecipeClickListener listener;

  public CookbookRecyclerAdapter(Context context, List<Recipe> recipes, OnRecipeClickListener listener) {
    this.context = context;
    this.recipes = recipes;
    this.listener = listener;
  }

  public void updateRecies(List<Recipe> newRecipes) {
    recipes.clear();
    recipes.addAll(newRecipes);
    notifyDataSetChanged();
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(context);
    CookbookListItemBinding cookbookListItemBinding = CookbookListItemBinding.inflate(layoutInflater, parent, false);
    return new ViewHolder(cookbookListItemBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Recipe recipe = recipes.get(position);
    holder.bind(position, recipe);
  }

  @Override
  public int getItemCount() {
    return recipes.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private final View clickView;

    private CookbookListItemBinding binding;

    public ViewHolder(CookbookListItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      clickView = binding.clickView;
    }

    public void bind(int position, Recipe recipe) {
    binding.setVariable(edu.cnm.deepdive.choppit.BR.recipe, recipe);
    clickView.setOnClickListener((v) -> {});
    binding.executePendingBindings();
    }
  }
  @FunctionalInterface
  public interface OnRecipeClickListener{

    void onRecipeClick(int position, Recipe recipe);
  }
}