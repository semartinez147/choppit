package com.semartinez.projects.choppit.controller.ui.cookbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.databinding.FragmentRecipeBinding;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.view.IngredientRecyclerAdapter;
import com.semartinez.projects.choppit.view.StepRecyclerAdapter;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;


/**
 * RecipeFragment displays a single {@link Recipe}.
 */
public class RecipeFragment extends Fragment {

  private MainViewModel viewModel;
  private FragmentRecipeBinding binding;
  private Recipe recipe;

  /**
   * This override of onCreateView creates bindings for the fragment UI. It also  retrieves a
   * reference to the {@link MainViewModel} and requests a {@link Recipe} from it.  The result is
   * loaded into the UI binding and Recycler View.
   */
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    RecipeFragmentArgs args = RecipeFragmentArgs.fromBundle(requireArguments());

    binding = FragmentRecipeBinding.inflate(inflater);
    binding.setLifecycleOwner(this);
    binding.setBindViewModel(viewModel);
    binding.setUiController(this);

    viewModel.loadRecipe(args.getRecipeId());
    viewModel.getRecipe().observe(getViewLifecycleOwner(), result -> {
      if (result != null) {
        recipe = viewModel.getRecipe().getValue();
        setupRecyclerView();
        binding.setRecipe(recipe);
      }
    });
    return binding.getRoot();
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }

  @SuppressWarnings("DuplicatedCode")
  private void setupRecyclerView() {
    RecyclerView ingredientRecyclerView = binding.recipeIngredientRecyclerView;
    RecyclerView stepRecyclerView = binding.recipeStepRecyclerView;
    LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(getContext());
    LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext());
    IngredientRecyclerAdapter ingredientRecyclerAdapter = new IngredientRecyclerAdapter(
        getContext(), recipe,
        this);
    StepRecyclerAdapter stepRecyclerAdapter = new StepRecyclerAdapter(getContext(), recipe, this);
    ingredientRecyclerView.setLayoutManager(ingredientLayoutManager);
    stepRecyclerView.setLayoutManager(stepLayoutManager);
    ingredientRecyclerView.setAdapter(ingredientRecyclerAdapter);
    stepRecyclerView.setAdapter(stepRecyclerAdapter);
  }


}
