package com.semartinez.projects.choppit.controller.ui.editing;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.MainActivity;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;

public class LoadingFragment extends Fragment {

  private MainViewModel viewModel;
  private static String url;
  private static String ingredient;
  private static String instruction;
  private TextView status;
  private boolean fromHome;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    assert getArguments() != null;
    url = LoadingFragmentArgs.fromBundle(getArguments()).getUrl();
    ingredient = LoadingFragmentArgs.fromBundle(getArguments()).getIngredient();
    instruction = LoadingFragmentArgs.fromBundle(getArguments()).getInstruction();
    fromHome = LoadingFragmentArgs.fromBundle(getArguments()).getFrom().equals("home");
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    Log.d("LoadingFrag", "onCreateView - " + url + " " + ingredient + " " + instruction);
    View root = inflater.inflate(R.layout.fragment_loading, container, false);
    status = root.findViewById(R.id.status);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (fromHome) {
      viewModel.resetData();
      viewModel.makeItGo(url);
    } else {
      ((MainActivity) getActivity()).showToast("fromHome = false");// TODO what happens coming back from the SelectionFragment.
      viewModel.processData(ingredient, instruction);
    }
    observe();
  }

  private void observe() {
    viewModel.getStatus().observe(getViewLifecycleOwner(), statusObserver);
  }

  final Observer<String> statusObserver = new Observer<String>() {
    @Override
    public void onChanged(String s) {
      switch (s) {
        case "":
          status.setText(R.string.warming_up);
          break;
        case "connecting":
          Log.d("LoadingFrag", "connecting");
          status.setText(R.string.connecting);
          observe();
          break;
        case "connected":
          status.setText(R.string.separating);
          viewModel.generateHtml();
          viewModel.getHtml().observe(getViewLifecycleOwner(), h -> {
            LoadingFragmentDirections.LoadSel select = LoadingFragmentDirections.loadSel()
                .setHtml(String.valueOf(h));
            Navigation.findNavController(getView()).navigate(select);
          });
          break;
//        case "gathering":
//          Log.d("LoadingFrag", "gathering");
//          viewModel.processData(ingredient, instruction);
//          break;
        case "processing":
          status.setText(R.string.processing);
          Log.d("LoadingFrag", "processing");
          break;
        case "finishing":
          status.setText(R.string.finishing);
          viewModel.postRecipe();
          break;
        case "finished":
          viewModel.getRecipe().observe(getViewLifecycleOwner(), recipeObserver);
      }
    }
  };

  /**
   * Navigates forward when processing is complete.
   */
  final Observer<Recipe> recipeObserver = recipe -> {
    if (recipe != null && getView() != null) {
      Navigation.findNavController(getView()).navigate(LoadingFragmentDirections.loadEdit());
    }
  };

}
