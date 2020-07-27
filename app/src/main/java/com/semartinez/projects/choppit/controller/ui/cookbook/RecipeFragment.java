package com.semartinez.projects.choppit.controller.ui.cookbook;

import static com.semartinez.projects.choppit.BR.bindViewModel;
import static com.semartinez.projects.choppit.BR.uiController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.semartinez.projects.choppit.view.RecipeRecyclerAdapter;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;


public class RecipeFragment extends Fragment {

  RecipeRecyclerAdapter recipeRecyclerAdapter;
  private MainViewModel viewModel;
  private FragmentRecipeBinding binding;
  private Recipe recipe;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);


  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }

  private void setupRecyclerView() {
    RecyclerView recyclerView = binding.recipeRecyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recipeRecyclerAdapter = new RecipeRecyclerAdapter(getContext(), recipe);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(recipeRecyclerAdapter);
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {

    RecipeFragmentArgs args = RecipeFragmentArgs.fromBundle(getArguments());
    viewModel.loadRecipe(args.getRecipeId());
    viewModel.getRecipe().observe(getViewLifecycleOwner(), recipeObserver);

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);
    binding.setLifecycleOwner(this);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);

    return binding.getRoot();
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
