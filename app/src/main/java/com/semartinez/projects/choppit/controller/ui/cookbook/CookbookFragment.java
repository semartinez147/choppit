package com.semartinez.projects.choppit.controller.ui.cookbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.MainActivity;
import com.semartinez.projects.choppit.databinding.FragmentCookbookBinding;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.view.CookbookRecyclerAdapter;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
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
    cookbookRecyclerAdapter = new CookbookRecyclerAdapter(getContext(), recipes);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(cookbookRecyclerAdapter);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentCookbookBinding.inflate(inflater);
    assert getActivity() != null;
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    binding.setBindViewModel(viewModel);
    binding.setUiController(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    viewModel.getAllRecipes().observe(getViewLifecycleOwner(), cookbookObserver);

  }

  /**
   * This observer resets the dataset if there is a change to the list of {@link Recipe}s.
   */
  final Observer<List<Recipe>> cookbookObserver = new Observer<List<Recipe>>() {

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