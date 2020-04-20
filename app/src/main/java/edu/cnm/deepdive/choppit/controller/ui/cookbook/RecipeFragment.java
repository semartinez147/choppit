package edu.cnm.deepdive.choppit.controller.ui.cookbook;

import static edu.cnm.deepdive.choppit.BR.bindViewModel;
import static edu.cnm.deepdive.choppit.BR.uiController;

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
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.databinding.FragmentRecipeBinding;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.view.RecipeRecyclerAdapter;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;


public class RecipeFragment extends Fragment {

  RecipeRecyclerAdapter recipeRecyclerAdapter;
  private MainViewModel viewModel;
  private Recipe recipe;
  private FragmentRecipeBinding binding;


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
   * This observer checks the {@link MainViewModel} for an updated recipe before navigating to the
   * {@link RecipeFragment}
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
