package edu.cnm.deepdive.choppit.controller.ui.cookbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.databinding.FragmentCookbookBinding;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.view.CookbookRecyclerAdapter;
import edu.cnm.deepdive.choppit.view.CookbookRecyclerAdapter.OnRecipeClickListener;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;

// TODO implement onClickListener to toggle recipe favorite state
// TODO access editing screen via long press or pencil icon

/**
 * Displays the list of {@link Recipe}s stored in the local database
 */
public class CookbookFragment extends Fragment implements OnRecipeClickListener{

  CookbookRecyclerAdapter cookbookRecyclerAdapter;
  private MainViewModel viewModel;
  private final List<Recipe> recipes = new ArrayList<>();
  public FragmentCookbookBinding binding;


  private void setupRecyclerView() {
    RecyclerView recyclerView = binding.recipeList;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    cookbookRecyclerAdapter = new CookbookRecyclerAdapter(getContext(), recipes,
       this);
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

    binding = FragmentCookbookBinding.inflate(inflater);
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

  @Override
  public void onRecipeClick(int position) {
    viewModel.loadRecipe(recipes.get(position).getId());
    viewModel.getRecipe().observe(getViewLifecycleOwner(), recipeObserver);

  }

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

  final Observer<Recipe> recipeObserver = new Observer<Recipe>() {

    @Override
    public void onChanged(final Recipe result) {
      if (result != null) {
        Navigation.findNavController(getView()).navigate(R.id.cook_rec);
      } else {
        ((MainActivity) getActivity()).showToast("Recipe failed to load!");
      }
    }
  };

}