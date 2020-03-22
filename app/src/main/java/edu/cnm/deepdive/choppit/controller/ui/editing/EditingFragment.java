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
import androidx.lifecycle.ViewModelProviders;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditingFragment extends Fragment {

  EditingRecyclerAdapter editingRecyclerAdapter;
  private MainViewModel viewModel;
  private JsoupRetriever retriever;
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
    Log.d("databinding", "completed");
    setupRecyclerView();
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }

  private void setupRecyclerView() {
    Log.d("Recylcerview", "starting setup");

    RecyclerView recyclerView = binding.editingRecyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    editingRecyclerAdapter = new EditingRecyclerAdapter(getContext(), ingredients, steps);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(editingRecyclerAdapter);
    Log.d("Recylcerview", "set up");
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
    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    binding.setVariable(bindViewModel, viewModel);
    binding.setVariable(uiController, this);
//    retriever = JsoupRetriever.getInstance();
//    this.ingredients = retriever.getIngredients();
//    this.steps = retriever.getSteps();
    return binding.getRoot();

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Button continue_button = view.findViewById(R.id.editing_continue);
    continue_button.setOnClickListener(v -> {
      ((MainActivity) getActivity())
          .navigateTo(R.id.navigation_cookbook); //TODO change this to the active recipe
    });

    beginJsoupProcessing();


//    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
//    viewModel.getIngredients().observe(getViewLifecycleOwner(), ingredients -> {
//      if (ingredients != null) {
//        editingRecyclerAdapter.updateIngredients(ingredients);
//      }
//    });
//    viewModel.getSteps().observe(getViewLifecycleOwner(), steps -> {
//      if (steps != null) {
//        editingRecyclerAdapter.updateSteps(steps);
//      }
//    });

    viewModel.getIngredients().observe(getViewLifecycleOwner(), ingredientObserver);
    viewModel.getSteps().observe(getViewLifecycleOwner(), stepObserver);

  }

  private void beginJsoupProcessing() {
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    try {
      viewModel.retrieve();
    } catch (IOException e) {
      e.printStackTrace();
    }
    viewModel.gatherIngredients();
    viewModel.gatherSteps();
  }

  final Observer<List<Ingredient>> ingredientObserver = new Observer<List<Ingredient>>() {

    @Override
    public void onChanged(final List<Ingredient> result) {
      ingredients.addAll(result);
      editingRecyclerAdapter.notifyDataSetChanged();
    }
  };

  final Observer<List<Step>> stepObserver = new Observer<List<Step>>() {
    @Override
    public void onChanged(List<Step> result) {
      steps.addAll(result);
      editingRecyclerAdapter.notifyDataSetChanged();
    }
  };

}
