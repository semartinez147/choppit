package edu.cnm.deepdive.choppit.controller.ui.editing;

import static edu.cnm.deepdive.choppit.BR.bindViewModel;
import static edu.cnm.deepdive.choppit.BR.uiController;

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
import edu.cnm.deepdive.choppit.databinding.FragmentEditingBinding;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.service.JsoupRetriever;
import edu.cnm.deepdive.choppit.view.EditingRecyclerAdapter;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;

public class EditingFragment extends Fragment {

  EditingRecyclerAdapter editingRecyclerAdapter;
  private MainViewModel viewModel;
  private List<Ingredient> ingredients = new ArrayList<>();
  private List<Step> steps = new ArrayList<>();
  private FragmentEditingBinding binding;

  public static EditingFragment createInstance() {
    EditingFragment fragment = new EditingFragment();
    Bundle args = new Bundle();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setRetainInstance(true);
    binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_editing);
    Log.d("EditingFrag", "databinding completed");
    setupRecyclerView();
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }

  private void setupRecyclerView() {
    RecyclerView recyclerView = binding.editingRecyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    editingRecyclerAdapter = new EditingRecyclerAdapter(getContext(), ingredients, steps);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(editingRecyclerAdapter);
    Log.d("EditingFrag", "RecyclerView set up");
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
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Log.d("EditingFrag", "onViewCreated");
    Button continue_button = view.findViewById(R.id.editing_continue);
    viewModel.getIngredients().observe(getViewLifecycleOwner(), ingredientObserver);
    viewModel.getSteps().observe(getViewLifecycleOwner(), stepObserver);

    continue_button.setOnClickListener(v -> {
      ((MainActivity) getActivity())
          .navigateTo(R.id.navigation_cookbook); //TODO add recipe to databse & navigate to cooking screen
    });
  }

  final Observer<List<Ingredient>> ingredientObserver = new Observer<List<Ingredient>>() {

    @Override
    public void onChanged(final List<Ingredient> result) {
      Log.d("EditingFrag", "ingredientObserver");
      if (result != null) {
      ingredients.clear();
      ingredients.addAll(result);
      editingRecyclerAdapter.notifyDataSetChanged();
      Log.d("EditingFrag", "Observed ingredients:" + ingredients.size());
      } else {
        Log.d("EditingFrag", "ingredients are null");
      }
    }
  };

  final Observer<List<Step>> stepObserver = new Observer<List<Step>>() {
    @Override
    public void onChanged(List<Step> result) {
      Log.d("EditingFrag", "stepObserver");
      if (result != null) {
      steps.clear();
      steps.addAll(result);
      editingRecyclerAdapter.notifyDataSetChanged();
      Log.d("EditingFrag", "Observed steps:" + steps.size());
      } else {
        Log.d("EditingFrag", "ingredients are null");
      }
    }
  };



}
