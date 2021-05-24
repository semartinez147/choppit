package com.semartinez.projects.choppit.controller.ui.editing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.MainActivity;
import com.semartinez.projects.choppit.controller.ui.dialog.SelectDialog;
import com.semartinez.projects.choppit.databinding.FragmentSelectionBinding;
import com.semartinez.projects.choppit.view.SelectionRecyclerAdapter;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;


/**
 * SelectionFragment displays the list of text elements scraped by Jsoup and prompts the user to
 * select a step and an ingredient from the list so that matching elements can be retrieved.
 */
public class SelectionFragment extends Fragment {

  private FragmentSelectionBinding binding;
  private List<String> strings;
  private MainViewModel viewModel;


  //  TODO: experiment with this.
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
  }

  /**
   * This override of onCreateView inflates the Fragment using View Binding, retrieves a reference
   * to the {@link MainViewModel} to clear its status field (which the LoadingFragment reads) and
   * display the text elements scraped from the HTML.
   */
  @Override
  public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    viewModel.resetStatus();
    strings = (viewModel.getStringsFromDocument().getValue() != null ? viewModel
        .getStringsFromDocument().getValue()
        : Collections.singletonList("No text retrieved"));
    binding = FragmentSelectionBinding.inflate(inflater);
    binding.selectionExtract.setOnClickListener(v -> {
      sendToLoading(binding.ingredientInput.getText().toString(),
          binding.stepInput.getText().toString());
    });
    setupRecyclerView();
    return binding.getRoot();
  }

  /**
   * This override of onViewCreated inserts preset text values to simplify development and
   * debugging.  It should be removed for production.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //  DEV pre-fill text input with strings from test recipe
    binding.ingredientInput.setText("1/2 pound elbow macaroni");
    binding.stepInput.setText("oven to 350");
  }

  private void setupRecyclerView() {
    RecyclerView selectionRecyclerView = binding.selectionRecyclerView;
    SelectionRecyclerAdapter selectionRecyclerAdapter = new SelectionRecyclerAdapter(
        requireContext(),
        strings, this);
    selectionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    selectionRecyclerView.setAdapter(selectionRecyclerAdapter);
  }


  /**
   * Generates a DialogFragment that handles designating a specific string as Step or Ingredient
   * text.
   *
   * @param text is the content of the user's selection.
   */
  public void markString(String text) {
    new SelectDialog(text, this)
        .show(requireActivity().getSupportFragmentManager(), SelectDialog.class.getName());
  }


  /**
   * Populates the Ingredient field with text from {@link SelectDialog}
   * @param ingredient is the string received from SelectDialog.
   */
  public void markAsIngredient(String ingredient) {
    binding.ingredientInput.setText(ingredient);
  }

  /**
   * Populates the Step field with text from {@link SelectDialog}
   * @param instruction is the string received from SelectDialog.
   */
  public void markAsStep(String instruction) {
    binding.stepInput.setText(instruction);
  }

  private void sendToLoading(String ingredient, String instruction) {
    if (!ingredient.isEmpty() && !instruction.isEmpty()) {
      viewModel.processData(ingredient, instruction);
      Navigation.findNavController(requireView()).navigate(SelectionFragmentDirections.selLoad());
    } else {
      ((MainActivity) requireActivity()).showToast(getString(R.string.no_string_selected));
    }

  }

}
