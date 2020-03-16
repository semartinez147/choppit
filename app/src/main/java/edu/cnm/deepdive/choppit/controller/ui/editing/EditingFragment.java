package edu.cnm.deepdive.choppit.controller.ui.editing;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.controller.ui.cookbook.CookbookFragment;
import edu.cnm.deepdive.choppit.view.IngredientListAdapter;
import edu.cnm.deepdive.choppit.view.StepListAdapter;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditingFragment extends Fragment {

  ListView ingredientList;
  ListView stepList;
  private MainViewModel viewModel;

  private List<String> measurement = new ArrayList<>();
  private List<String> unit = new ArrayList<>();
  private List<String> name = new ArrayList<>();
  private List<String> step = new ArrayList<>();

  public static EditingFragment createInstance() {
    EditingFragment fragment = new EditingFragment();
    Bundle args = new Bundle();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setHasOptionsMenu(true);
    setRetainInstance(true);


    }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);

    inflater.inflate(R.menu.help_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      // TODO generate popup with editing instructions
     }
    return super.onOptionsItemSelected(item);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_editing, container, false);
    ingredientList = root.findViewById(R.id.ingredient_list);
    stepList = root.findViewById(R.id.step_list) ;
// TODO switch to Recylcer View?

    IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(this.getActivity(),
        measurement, unit, name);
    ingredientList.setAdapter(ingredientListAdapter);

    StepListAdapter stepListAdapter = new StepListAdapter(this.getActivity(), step);
    stepList.setAdapter(stepListAdapter);

    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Button continue_button = view.findViewById(R.id.editing_continue);
    continue_button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity())
            .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, new CookbookFragment());
        fragmentTransaction.addToBackStack("EditingFragment");
        fragmentTransaction.commit();
      }
    });
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
