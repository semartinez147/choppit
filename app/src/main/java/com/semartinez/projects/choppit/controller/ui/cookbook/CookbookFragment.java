package com.semartinez.projects.choppit.controller.ui.cookbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.semartinez.projects.choppit.controller.ui.dialog.DeleteDialog;
import com.semartinez.projects.choppit.databinding.FragmentCookbookBinding;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.view.CookbookRecyclerAdapter;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Cookbook Fragment displays the list of {@link Recipe}s stored in the local database
 */
public class CookbookFragment extends Fragment {

  private CookbookRecyclerAdapter cookbookRecyclerAdapter;
  private MainViewModel viewModel;
  private final List<Recipe> recipes = new ArrayList<>();
  private FragmentCookbookBinding binding;


  /**
   * This override of onCreateView creates bindings for the fragment UI.  It also retrieves a
   * reference to the {@link MainViewModel} and requests the complete {@link Recipe} list from it.
   * The result is loaded into {@link #recipes}, then the Recycler View and Recycler Adapter are
   * created with that data.
   */
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    binding = FragmentCookbookBinding.inflate(inflater);
    binding.setBindViewModel(viewModel);
    binding.setUiController(this);

    viewModel.getAllRecipes().observe(getViewLifecycleOwner(), result -> {
      if (result != null) {
        recipes.clear();
        recipes.addAll(result);
        setupRecyclerView();
        cookbookRecyclerAdapter.notifyDataSetChanged();
      }
    });

    return binding.getRoot();
  }

  private void setupRecyclerView() {
    RecyclerView recyclerView = binding.recipeList;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    cookbookRecyclerAdapter = new CookbookRecyclerAdapter(getContext(), recipes, this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(cookbookRecyclerAdapter);
  }


  /**
   * Opens a Dialog with an option to delete the recipe
   *
   * @param recipe takes a Recipe for possible deletion.
   */
  public void deleteRecipe(Recipe recipe) {
    new DeleteDialog(recipe, viewModel)
        .show(requireActivity().getSupportFragmentManager(), recipe.getTitle() + " delete");
  }

}
