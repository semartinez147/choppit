package edu.cnm.deepdive.choppit.controller.ui.cookbook;

import static edu.cnm.deepdive.choppit.BR.bindViewModel;
import static edu.cnm.deepdive.choppit.BR.uiController;

import android.os.Bundle;
import android.util.Log;
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
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.view.RecipeRecyclerAdapter;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;


public class RecipeFragment extends Fragment {

  RecipeRecyclerAdapter recipeRecyclerAdapter;
  private MainViewModel viewModel;
  RecyclerView recyclerView;
  private Recipe recipe; // TODO where does the recipe come from?
  private List<Ingredient> ingredients = new ArrayList<>();
  private List<Step> steps;
  private FragmentRecipeBinding binding;

  /**
   * Loads and displays a single {@link Recipe} from the local database using Data Binding to
   * populate each list item.
   *
   * @param recipe the recipe to be displayed
   */
  public RecipeFragment(Recipe recipe) {
    if (recipe != null){
    this.recipe = recipe;
} else {
      this.recipe = viewModel.getRecipe().getValue();
    }
    this.steps = recipe.getSteps();
    for (Step step : steps) {
      ingredients.addAll(step.getIngredients());
    }
  }


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_recipe);
    setupRecyclerView();
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }

  private void setupRecyclerView() {
    RecyclerView recyclerview = binding.recipeRecyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recipeRecyclerAdapter = new RecipeRecyclerAdapter(getContext(), recipe);
    recyclerview.setLayoutManager(layoutManager);
    recyclerView.setAdapter(recipeRecyclerAdapter);
    Log.d("RecipeFrag", "RecyclerView set up");
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(recipe.getTitle());
    FragmentRecipeBinding binding;
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);
    binding.setLifecycleOwner(this);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }
}
