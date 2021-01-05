package com.semartinez.projects.choppit.controller.ui.editing;

import static com.semartinez.projects.choppit.BR.bindViewModel;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.ui.SaveDialog;
import com.semartinez.projects.choppit.databinding.FragmentEditingBinding;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.view.IngredientRecyclerAdapter;
import com.semartinez.projects.choppit.view.StepRecyclerAdapter;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;

public class EditingFragment extends Fragment {

  private MainViewModel viewModel;
  private FragmentEditingBinding binding;
  private Recipe recipe;
  private IngredientRecyclerAdapter ingredientRecyclerAdapter;
  private StepRecyclerAdapter stepRecyclerAdapter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    assert getActivity() != null;
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

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

    EditingFragmentArgs args = EditingFragmentArgs.fromBundle(requireArguments());
    if (args.getRecipeId() != 0L) {
      viewModel.loadRecipe(args.getRecipeId());
    }
    viewModel.getRecipe().observe(getViewLifecycleOwner(), result -> {
      if (result != null) {
        recipe = viewModel.getRecipe().getValue();
      } else {
        recipe = Recipe.getEmptyRecipe();
      }
      setupRecyclerView();
      binding.setRecipe(recipe);
    });

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editing, container, false);
    binding.setLifecycleOwner(this);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);

    Button saveButton = binding.editingSave;
    saveButton.setOnClickListener(v -> {
      if (recipe.getRecipeId() == 0) {
        viewModel.saveRecipe(recipe);
        navAfterSave(getView());
      } else {
        showSaveDialog(v);
      }
    });

    return binding.getRoot();
  }

  @SuppressWarnings("DuplicatedCode")
  private void setupRecyclerView() {
    RecyclerView ingredientRecyclerView = binding.editingIngredientRecyclerView;
    RecyclerView stepRecyclerView = binding.editingStepRecyclerView;
    LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(getContext());
    LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext());
    ingredientRecyclerAdapter = new IngredientRecyclerAdapter(getContext(), recipe,
        this);
    stepRecyclerAdapter = new StepRecyclerAdapter(getContext(), recipe, this);
    ingredientRecyclerView.setLayoutManager(ingredientLayoutManager);
    stepRecyclerView.setLayoutManager(stepLayoutManager);
    ingredientRecyclerView.setAdapter(ingredientRecyclerAdapter);
    stepRecyclerView.setAdapter(stepRecyclerAdapter);
  }

  public void navAfterSave(View v) {
    viewModel.resetData();
    Navigation.findNavController(v).navigate(R.id.edit_cook);
  }


  @SuppressWarnings("unused")
  //View parameter is required by databinding onClick function
  public void addStep(View view) {
    stepRecyclerAdapter.addStep();
  }

  @SuppressWarnings("unused")
  //View parameter is required by databinding onClick function
  public void addIngredient(View view) {
    ingredientRecyclerAdapter.addIngredient();
  }

  public void deleteStep(int position) {
    stepRecyclerAdapter.deleteStep(position);
  }

  public void deleteIngredient(int position) {
    Log.d("deleteIngredient", "method call");
    ingredientRecyclerAdapter.deleteIngredient(position);
  }

  private void showSaveDialog(View view) {
    SaveDialog save = new SaveDialog(recipe, view, viewModel);
    save.show(getChildFragmentManager(), SaveDialog.class.getName());
  }

  public Recipe getRecipe() {
    return recipe;
  }
}
