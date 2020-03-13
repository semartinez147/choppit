package edu.cnm.deepdive.choppit.controller.ui.editing;

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
import edu.cnm.deepdive.choppit.controller.ui.cookbook.CookbookFragment;
import edu.cnm.deepdive.choppit.view.IngredientListAdapter;
import edu.cnm.deepdive.choppit.view.StepListAdapter;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.Objects;

public class EditingFragment extends Fragment {

  ListView ingredientList;
  ListView stepList;
  private View root;
  private View list;
  private MainViewModel viewModel;

  private Double[] measurement = {8.0, 12.0, 1.0, 1.0, 2.0, 8.0, 1.0, 1.0, 1.0, 1.5, 12.0};
  private String[] unit = {"ounces", "ounces", "teaspoon", "teaspoon", "ounces", "ounces", "",
      "", "ounce", "teaspoons", "ounces"};
  private String[] name = {"unsalted butter", "bread flour", "kosher salt", "baking soda",
      "granulated sugar", "light brown sugar", "large egg", "large egg yolk", "whole milk",
      "vanilla extract", "semisweet chocolate chips"};
  private String[] step = {"", "", ""};

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
    root = inflater.inflate(R.layout.fragment_editing, container, false);
// TODO switch to Recylcer View?

    IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(this.getActivity(),
        measurement, unit, name);
    ingredientList = root.findViewById(R.id.ingredient_list);
    ingredientList.setAdapter(ingredientListAdapter);

    StepListAdapter stepListAdapter = new StepListAdapter(this.getActivity(), step);
    stepList = root.findViewById(R.id.step_list) ;
    stepList.setAdapter(stepListAdapter);

    Button continue_button = (Button) root.findViewById(R.id.editing_continue);
    continue_button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity())
            .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new CookbookFragment());
        fragmentTransaction.addToBackStack("EditingFragment");
        fragmentTransaction.commit();
      }
    });
    return root;
  }
}
