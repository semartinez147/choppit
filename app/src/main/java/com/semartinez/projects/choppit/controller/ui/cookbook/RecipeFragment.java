package com.semartinez.projects.choppit.controller.ui.cookbook;

import static com.semartinez.projects.choppit.BR.bindViewModel;
import static com.semartinez.projects.choppit.BR.uiController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.databinding.FragmentRecipeBinding;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.view.IngredientRecyclerAdapter;
import com.semartinez.projects.choppit.view.StepRecyclerAdapter;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;


public class RecipeFragment extends Fragment {

  private MainViewModel viewModel;
  private FragmentRecipeBinding binding;
  private Recipe recipe;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    assert getActivity() != null;
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);


  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    assert getArguments() != null;
    RecipeFragmentArgs args = RecipeFragmentArgs.fromBundle(getArguments());
    viewModel.loadRecipe(args.getRecipeId());
    viewModel.getRecipe().observe(getViewLifecycleOwner(), recipeObserver);

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);
    binding.setLifecycleOwner(this);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);

    return binding.getRoot();
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    return super.onOptionsItemSelected(item);
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

  /**
   * This observer checks the {@link MainViewModel} for an updated recipe and sets its contents to
   * the field {@link RecipeFragment#recipe}.
   */
  final Observer<Recipe> recipeObserver = new Observer<Recipe>() {

    @Override
    public void onChanged(final Recipe result) {
      if (result != null) {
        recipe = viewModel.getRecipe().getValue();
        setupRecyclerView();
        binding.setRecipe(recipe);
      }
    }
  };


}
