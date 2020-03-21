package edu.cnm.deepdive.choppit.controller.ui.editing;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditingFragment extends Fragment {

  EditingRecyclerAdapter adapter;
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
    setRetainInstance(true);
    binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_editing);

  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }

  private void setupRecyclerView() {

    RecyclerView recyclerView = binding.editingRecyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    EditingRecyclerAdapter adapter = new EditingRecyclerAdapter(getContext(), ingredients, steps);
    recyclerView.setAdapter(adapter);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(R.string.recipe_editing);

   setupRecyclerView();

    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    makeJsoupGo();
    Button continue_button = view.findViewById(R.id.editing_continue);
    continue_button.setOnClickListener(v -> {
      ((MainActivity) getActivity())
          .navigateTo(R.id.navigation_cookbook); //TODO change this to the active recipe
    });
  }


  public void makeJsoupGo() {
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    try {
      viewModel.retrieve();
    } catch (IOException e) {
      e.printStackTrace();
    }
    viewModel.gatherIngredients();
    for (Ingredient ingredient : ingredients) {
      Log.d("Gathered ingredients", ingredient.getName());
    }
    viewModel.gatherSteps();
    viewModel.getIngredients().observe(getViewLifecycleOwner(), ingredients -> {
      if (ingredients != null) {
        adapter.updateIngredients(ingredients);
      }
    });
    viewModel.getSteps().observe(getViewLifecycleOwner(), steps -> {
      if (steps != null) {
        adapter.updateSteps(steps);
      }
    });

  }
}
