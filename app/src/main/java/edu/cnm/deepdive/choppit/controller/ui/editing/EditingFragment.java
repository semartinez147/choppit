package edu.cnm.deepdive.choppit.controller.ui.editing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.view.IngredientListAdapter;
import edu.cnm.deepdive.choppit.view.StepListAdapter;

public class EditingFragment extends Fragment {

  ListView ingredientList;
  ListView stepList;
  private View view;
  private EditingViewModel editingViewModel;

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

    // TODO generate popup with editing instructions
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
     }
    return super.onOptionsItemSelected(item);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_editing, container, false);

    IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(this.getActivity(),
        measurement, unit, name);
    ingredientList = (ListView) root.findViewById(R.id.ingredient_list);
    ingredientList.setAdapter(ingredientListAdapter);

    /*StepListAdapter stepListAdapter = new StepListAdapter(this.getActivity(), step);
    stepList = (ListView) root.findViewById(R.id.step_list) ;
    ingredientList.setAdapter(stepListAdapter);
*/
    return root;
  }
}
