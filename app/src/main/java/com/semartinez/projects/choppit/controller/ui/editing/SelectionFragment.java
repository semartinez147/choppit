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


/*
 * TODO update docs in this Fragment
 */
public class SelectionFragment extends Fragment {

  private FragmentSelectionBinding binding;
  private List<String> strings;
  private MainViewModel viewModel;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
    viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    viewModel.resetStatus();
  }

  @Override
  public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    strings = (viewModel.getStringsFromDocument().getValue() != null ? viewModel
        .getStringsFromDocument().getValue()
        : Collections.singletonList("No text retrieved"));
    binding = FragmentSelectionBinding.inflate(inflater);
    binding.selectionExtract.setOnClickListener(v -> {
      sendToLoading(binding.ingredientInput.getText().toString(), binding.stepInput.getText().toString());
    });
    setupRecyclerView();
    return binding.getRoot();
  }

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

  public void markString(String text) {
    new SelectDialog(text, this)
        .show(requireActivity().getSupportFragmentManager(), SelectDialog.class.getName());
  }

  public void markAsIngredient(String ingredient) {
    binding.ingredientInput.setText(ingredient);
  }

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
