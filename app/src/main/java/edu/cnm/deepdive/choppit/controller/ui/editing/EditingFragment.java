package edu.cnm.deepdive.choppit.controller.ui.editing;

import static edu.cnm.deepdive.choppit.BR.bindViewModel;
import static edu.cnm.deepdive.choppit.BR.uiController;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.databinding.FragmentEditingBinding;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Ingredient.Unit;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.model.repository.RecipeRepository;
import edu.cnm.deepdive.choppit.view.EditingRecyclerAdapter;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// FIXME: error when navigating away from this screen.
// TODO: add itemDividerDecoration for RecyclerView headers
// TODO: better spacing of unit field
// TODO: populate empty fields for from-scratch recipes.
// TODO: allow saving a recipe


public class EditingFragment extends Fragment {

  EditingRecyclerAdapter editingRecyclerAdapter;
  private MainViewModel viewModel;
  private FragmentEditingBinding binding;
  private Recipe recipe;

  public EditingFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setRetainInstance(true);
    binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_editing);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    recipe = (viewModel.getRecipe().getValue() == null) ? emptyRecipe() : viewModel.getRecipe().getValue();
    setupRecyclerView();
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
    RecyclerView recyclerView = binding.editingRecyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    editingRecyclerAdapter = new EditingRecyclerAdapter(getContext(), recipe);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(editingRecyclerAdapter);
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
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(R.string.recipe_editing);

    FragmentEditingBinding binding;
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editing, container, false);
    binding.setLifecycleOwner(this);
    binding.setRecipe(recipe);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);
    binding.editingTitle.setText(recipe.getTitle());

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


/*
    v -> {
    viewModel.saveRecipe(recipe);
    Navigation.findNavController(view).navigate(R.id.edit_cook);
  }
  */
}
