package edu.cnm.deepdive.choppit.controller.ui.cookbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.view.RecipeListAdapter;


public class CookbookFragment extends Fragment {

  private CookbookViewModel cookbookViewModel;
  ListView recipeList;
  private View root;

  private String[] recipes = {"Chocolate chip cookies", "Turkish delight", "home-made sopapillas"};

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    root = inflater.inflate(R.layout.fragment_cookbook, container, false);

    RecipeListAdapter recipeListAdapter = new RecipeListAdapter(this.getActivity(), recipes);
    recipeList = (ListView) root.findViewById(R.id.recipe_list) ;
    recipeList.setAdapter(recipeListAdapter);

    return root;
  }
}