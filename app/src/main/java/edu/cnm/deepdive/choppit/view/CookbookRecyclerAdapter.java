package edu.cnm.deepdive.choppit.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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

  public CookbookRecyclerAdapter(Context context, List<Recipe> recipes) {
    this.context = context;
    this.recipes = recipes;
  }

  public void updateRecies(List<Recipe> newRecipes) {
    recipes.clear();
    recipes.addAll(newRecipes);
    notifyDataSetChanged();
  }

/*  @Nonnull
  public View getView(int position, View rowView, @Nonnull ViewGroup parent) {
    ViewHolder holder;
    view = rowView;
    // working from https://www.javacodegeeks.com/2013/09/android-viewholder-pattern-example.html

    if (view == null) {
      LayoutInflater inflater = context.getLayoutInflater();
      view = inflater.inflate(R.layout.cookbook_list_item, null, true);

      holder = new ViewHolder(view);
      holder.favorite = view.findViewById(R.id.favorite);
      holder.recipe = (TextView) view.findViewById(R.id.recipe_title);

      holder.edit = view.findViewById(R.id.edit);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    if (recipes.get(position) != null) {
      holder.favorite.setImageResource(R.drawable.ic_not_favorite);
      holder.recipe.setText(recipes.get(position).getTitle());
    }
    return view;
  }*/

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
    holder.bind(recipe);
  }

  @Override
  public int getItemCount() {
    return recipes.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private CookbookListItemBinding binding;

    public ViewHolder(CookbookListItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Recipe recipe) {
    binding.setVariable(edu.cnm.deepdive.choppit.BR.recipe, recipe);

    binding.executePendingBindings();
    }
  }
}