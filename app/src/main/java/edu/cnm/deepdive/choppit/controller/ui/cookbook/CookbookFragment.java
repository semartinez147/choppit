package edu.cnm.deepdive.choppit.controller.ui.cookbook;

import static edu.cnm.deepdive.choppit.BR.bindViewModel;
import static edu.cnm.deepdive.choppit.BR.uiController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.databinding.FragmentCookbookBinding;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.view.CookbookRecyclerAdapter;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;

// TODO implement onClickListener to toggle recipe favorite state
// TODO access editing screen via long press or pencil icon

/**
 * Displays the list of {@link Recipe}s stored in the local database
 */
public class CookbookFragment extends Fragment {

  CookbookRecyclerAdapter cookbookRecyclerAdapter;
  private MainViewModel viewModel;
  private final List<Recipe> recipes = new ArrayList<>();
  public FragmentCookbookBinding binding;


  private void setupRecyclerView() {
    RecyclerView recyclerView = binding.recipeList;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    cookbookRecyclerAdapter = new CookbookRecyclerAdapter(getContext(), recipes, (
        (position, recipe) -> viewRecipe(recipe.getId())));
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(cookbookRecyclerAdapter);
  }

  @SuppressWarnings("DuplicatedCode")
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(getString(R.string.cookbook));

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cookbook, container, false);
    binding.setLifecycleOwner(this);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    viewModel.getAllRecipes().observe(getViewLifecycleOwner(), recipeObserver);

  }

  private void viewRecipe(long recipeId) {
    ((MainActivity) getActivity())
        .navigateTo(R.id.navigation_recipe);
  }

  final Observer<List<Recipe>> recipeObserver = new Observer<List<Recipe>>() {

    @Override
    public void onChanged(final List<Recipe> result) {
      if (result != null) {
        recipes.clear();
        recipes.addAll(result);
        setupRecyclerView();
        cookbookRecyclerAdapter.notifyDataSetChanged();
      }
    }
  };
}