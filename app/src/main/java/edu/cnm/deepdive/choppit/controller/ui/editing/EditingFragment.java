package edu.cnm.deepdive.choppit.controller.ui.editing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.view.IngredientListAdapter;

public class EditingFragment  extends Fragment {

  private ListView ingredientList;
  private View view;
  private EditingViewModel editingViewModel;

  private String[] measurement = {};
  private String[] unit = {};
  private String[] name = {};

  public static EditingFragment createInstance() {
    EditingFragment fragment = new EditingFragment();
    Bundle args = new Bundle();
        return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(this.getActivity(), measurement, unit, name);

    ingredientList = (ListView) ingredientList.findViewById(R.id.ingredient_list);
    ingredientList.setAdapter(ingredientListAdapter);
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
    View layout = inflater.inflate(R.layout.fragment_editing, container, false);

    return layout;
  }
}
