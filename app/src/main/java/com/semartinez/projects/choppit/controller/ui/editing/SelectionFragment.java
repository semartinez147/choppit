package com.semartinez.projects.choppit.controller.ui.editing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.ui.home.HomeFragment;
import com.semartinez.projects.choppit.databinding.FragmentSelectionBinding;
import com.semartinez.projects.choppit.view.SelectionRecyclerAdapter;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nonnull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * This fragment loads a {@link WebView} of the contents of the {@link HomeFragment} url. Below the
 * WebView, it loads two text entry fields.  The inputted {@link String}s are used by {@link Jsoup}
 * to process the {@link Document} generated from the HTML
 */
public class SelectionFragment extends Fragment {

  private SelectionRecyclerAdapter selectionRecyclerAdapter;
  private FragmentSelectionBinding binding;
  private Set<String> strings;
  private MainViewModel viewModel;
  private EditText ingredientInput;
  private EditText stepInput;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
    viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
  }

  @Override
  public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    strings = (viewModel.getDocumentWithStrings().getValue().getStrings() != null? viewModel.getDocumentWithStrings().getValue().getStrings() : Collections.singleton("No text retrieved"));
    viewModel.getDocumentWithStrings().removeObservers(getViewLifecycleOwner());
    View root = inflater.inflate(R.layout.fragment_selection, container, false);

//    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selection, container, false);

    ingredientInput = root.findViewById(R.id.ingredient_input);
    stepInput = root.findViewById(R.id.step_input);
    Button continueButton = root.findViewById(R.id.selection_extract);
    continueButton.setOnClickListener(this::sendToLoading);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //  TODO disable for production
    ingredientInput.setText("1/2 pound elbow macaroni");
    stepInput.setText("oven to 350");


  }

  private void setupRecyclerView() {

  }

  public void markAsIngredient(int position) {
    ingredientInput.setText(selectionRecyclerAdapter.getStrings()[position]);
  }

  public void markAsStep(int position) {
    stepInput.setText(selectionRecyclerAdapter.getStrings()[position]);
  }

  private void sendToLoading(View v) {
    SelectionFragmentDirections.SelLoad load = SelectionFragmentDirections.selLoad()
        .setIngredient(ingredientInput.getText().toString())
        .setInstruction(stepInput.getText().toString())
        .setFrom("sel");
    Navigation.findNavController(v).navigate(load);
  }

}
