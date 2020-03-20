package edu.cnm.deepdive.choppit.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.choppit.R;
import javax.annotation.Nonnull;

public class CookbookListAdapter extends ArrayAdapter {

  private final Activity context;
  private final String[] recipes;
  private View view;

  public CookbookListAdapter(Activity context, String[] recipes) {
    super(context, R.layout.cookbook_list_item, recipes);
    this.context = context;
    this.recipes = recipes;
      }



  @Nonnull
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

      holder.edited = view.findViewById(R.id.edited);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    String recipeItem = recipes[position];
    if (recipeItem != null) {
      holder.favorite.setImageResource(R.drawable.ic_not_favorite);
      holder.recipe.setText(recipes[position]);
      holder.edited.setVisibility(View.GONE);
    }
    return view;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private ImageView favorite;
    private TextView recipe;
    private ImageView edited;

    private ViewHolder(@Nonnull View view) {
      super(view);
      favorite = view.findViewById(R.id.favorite);
        favorite.setImageResource(R.drawable.ic_not_favorite);
      recipe = view.findViewById(R.id.recipe_title);
      edited = view.findViewById(R.id.edited);
        edited.setImageResource(R.drawable.ic_edit);
    }

  }

}