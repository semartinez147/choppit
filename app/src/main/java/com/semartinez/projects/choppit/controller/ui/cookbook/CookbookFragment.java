package com.semartinez.projects.choppit.controller.ui.cookbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
 * Displays the list of {@link Recipe}s stored in the local database
 */
public class CookbookFragment extends Fragment {

  private CookbookRecyclerAdapter cookbookRecyclerAdapter;
  private MainViewModel viewModel;
  private final List<Recipe> recipes = new ArrayList<>();
  private FragmentCookbookBinding binding;


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentCookbookBinding.inflate(inflater);
    viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    binding.setBindViewModel(viewModel);
    binding.setUiController(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    viewModel.getAllRecipes().observe(getViewLifecycleOwner(), result -> {
      if (result != null) {
        recipes.clear();
        recipes.addAll(result);
        setupRecyclerView();
        cookbookRecyclerAdapter.notifyDataSetChanged();
      }
    });

  }

  private void setupRecyclerView() {
    RecyclerView recyclerView = binding.recipeList;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    cookbookRecyclerAdapter = new CookbookRecyclerAdapter(getContext(), recipes, this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(cookbookRecyclerAdapter);
  }

  // TODO change method to prompt "delete or edit?"
  public void deleteRecipe(Recipe recipe) {
    new DeleteDialog(recipe, viewModel).show(requireActivity().getSupportFragmentManager(), recipe.getTitle()+" delete");
  }

}
