package edu.cnm.deepdive.choppit.controller.ui.cookbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.view.EditingRecyclerAdapter;
import edu.cnm.deepdive.choppit.view.RecipeRecyclerAdapter;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

  RecipeRecyclerAdapter recipeRecyclerAdapter;
  private MainViewModel viewModel;
  RecyclerView recyclerView;
  private Recipe recipe; // TODO where does the recipe come from?
  private List<Ingredient> ingredients = new ArrayList<>();
  private List<Step> steps = new ArrayList<>();

  public static RecipeFragment createInstance() {
    RecipeFragment fragment = new RecipeFragment();
    Bundle args = new Bundle();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }


  @Nullable
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_recipe, container, false);
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(recipe.getTitle());
    recyclerView = root.findViewById(R.id.editing_recycler_view);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

  //  @Override
//  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//    super.onViewCreated(view, savedInstanceState);
//    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
//    viewModel.getRecipe().observe(getViewLifecycleOwner(), (recipe) -> {
//
//    });
//  }
}
