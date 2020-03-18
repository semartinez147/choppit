package edu.cnm.deepdive.choppit.controller.ui.editing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.service.JsoupRetriever;
import edu.cnm.deepdive.choppit.view.RecipeRecyclerAdapter;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;

public class EditingFragment extends Fragment {

  RecyclerView recyclerView;
  private MainViewModel viewModel;
  private JsoupRetriever retriever;
  private List<Ingredient> ingredients = new ArrayList<>();
  private List<Step> steps = new ArrayList<>();

  public static EditingFragment createInstance() {
    EditingFragment fragment = new EditingFragment();
    Bundle args = new Bundle();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.help_menu, menu);
  }


  @Nullable
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_editing, container, false);
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(R.string.recipe_editing);
    recyclerView = root.findViewById(R.id.editing_recycler_view);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Button continue_button = view.findViewById(R.id.editing_continue);
    continue_button.setOnClickListener(v -> {
      ((MainActivity) getActivity())
          .navigateTo(R.id.navigation_cookbook); //TODO change this to the active recipe
    });

    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.retrieve();
    viewModel.gatherIngredients();
    viewModel.gatherSteps();
    viewModel.getIngredients().observe(getViewLifecycleOwner(), (ingredients) -> {
      this.ingredients = ingredients;
    });
    viewModel.getSteps().observe(getViewLifecycleOwner(), (steps) -> {
      this.steps = steps;
    });
    RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(getContext(), ingredients, steps);
    recyclerView.setAdapter(adapter);
  }

  //  @Override
//  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//    super.onViewCreated(view, savedInstanceState);
//    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
//    viewModel.getRecipe().observe(getViewLifecycleOwner(), (recipe) -> {
//
//    });
//  }
}
