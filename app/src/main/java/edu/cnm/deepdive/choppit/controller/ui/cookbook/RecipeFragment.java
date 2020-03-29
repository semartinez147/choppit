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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
  private ActionBar actionBar;

  public RecipeFragment() {

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_recipe);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    recipe = viewModel.getRecipe().getValue();

    setupRecyclerView();
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
    actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(getString(R.string.recipe_screen));

    FragmentRecipeBinding binding;
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);
    binding.setLifecycleOwner(this);
    binding.setRecipe(recipe);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


  }


}
