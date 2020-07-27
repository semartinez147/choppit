package com.semartinez.projects.choppit.controller.ui.editing;

import static com.semartinez.projects.choppit.BR.bindViewModel;
import static com.semartinez.projects.choppit.BR.step;
import static com.semartinez.projects.choppit.BR.uiController;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.databinding.FragmentEditingBinding;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Ingredient.Unit;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import com.semartinez.projects.choppit.view.EditingIngredientRecyclerAdapter;
import com.semartinez.projects.choppit.view.EditingStepRecyclerAdapter;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import java.util.Arrays;

// FIXME: save button does not work.
// TODO: process new recipe through Repository to link Steps & Ingredients.


public class EditingFragment extends Fragment {

  EditingIngredientRecyclerAdapter editingIngredientRecyclerAdapter;
  private MainViewModel viewModel;
  private FragmentEditingBinding binding;
  private Recipe recipe;
  private EditingStepRecyclerAdapter editingStepRecyclerAdapter;
  private LinearLayoutManager ingredientLayoutManager;
  private LinearLayoutManager stepLayoutManager;

  public EditingFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

  }

  private Recipe emptyRecipe() {
    Recipe recipe = new Recipe();

    recipe.setSteps(Arrays.asList(
        new Step(recipe.getId(), "", 1, Arrays.asList(new Ingredient(0, "", Unit.C, null, ""))),
        new Step(recipe.getId(), "", 2, Arrays.asList(new Ingredient(0, "", Unit.C, null, ""))),
        new Step(recipe.getId(), "", 3, Arrays.asList(new Ingredient(0, "", Unit.C, null, "")))
        )
    );
    return recipe;
  }

  private void setupRecyclerView() {
    RecyclerView ingredientRecyclerView = binding.editingRecyclerView;
    RecyclerView stepRecyclerView = binding.editingRecyclerView2;
    ingredientLayoutManager = new LinearLayoutManager(getContext());
    stepLayoutManager = new LinearLayoutManager(getContext());
    editingIngredientRecyclerAdapter = new EditingIngredientRecyclerAdapter(getContext(), recipe,
        this);
    editingStepRecyclerAdapter = new EditingStepRecyclerAdapter(getContext(), recipe, this);
    ingredientRecyclerView.setLayoutManager(ingredientLayoutManager);
    stepRecyclerView.setLayoutManager(stepLayoutManager);
    ingredientRecyclerView.setAdapter(editingIngredientRecyclerAdapter);
    stepRecyclerView.setAdapter(editingStepRecyclerAdapter);
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    viewModel.getRecipe().observe(getViewLifecycleOwner(), recipeObserver);

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editing, container, false);
    binding.setLifecycleOwner(this);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);

    Button saveButton = binding.editingSave;
    saveButton.setOnClickListener(v -> {
      viewModel.saveRecipe(recipe);
      Navigation.findNavController(v).navigate(R.id.edit_cook);
    });

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }


  /**
   * This observer checks the {@link MainViewModel} for an updated recipe and sets its contents to
   * the field {@link EditingFragment#recipe}.
   */
  final Observer<Recipe> recipeObserver = new Observer<Recipe>() {

    @Override
    public void onChanged(final Recipe result) {
      if (result != null) {
        recipe = viewModel.getRecipe().getValue();
      } else {
        recipe = emptyRecipe();
      }
      setupRecyclerView();
      binding.setRecipe(recipe);
    }
  };

  public void addStep(View view) {
    editingStepRecyclerAdapter.addStep();
  }

  public void addIngredient(View view) {
    editingIngredientRecyclerAdapter.addIngredient();
  }

  public void deleteIngredient(int position) {
    Log.d("deleteIngredient", "method call");
    editingIngredientRecyclerAdapter.deleteIngredient(position);
  }

  public void deleteStep(int position) {
    editingStepRecyclerAdapter.deleteStep(position);
  }
}
