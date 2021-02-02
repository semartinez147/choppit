package com.semartinez.projects.choppit.controller.ui.editing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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

  private FragmentSelectionBinding binding;
  private Set<String> strings;
  private MainViewModel viewModel;

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
    strings = (viewModel.getDocumentWithStrings().getValue().getStrings() != null ? viewModel
        .getDocumentWithStrings().getValue().getStrings()
        : Collections.singleton("No text retrieved"));
    viewModel.getDocumentWithStrings().removeObservers(getViewLifecycleOwner());
    binding = FragmentSelectionBinding.inflate(inflater);
    binding.selectionExtract.setOnClickListener(this::sendToLoading);
    setupRecyclerView();
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //  TODO disable for production
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

  private void sendToLoading(View v) {
    if (binding.ingredientInput.getText().toString() != null
        && binding.stepInput.getText().toString() != null) {
      SelectionFragmentDirections.SelLoad load = SelectionFragmentDirections.selLoad()
          .setIngredient(binding.ingredientInput.getText().toString())
          .setInstruction(binding.stepInput.getText().toString())
          .setFrom("sel");
      Navigation.findNavController(v).navigate(load);
    } else {
      ((MainActivity) requireActivity()).showToast(getString(R.string.no_string_selected));
    }

  }

}
