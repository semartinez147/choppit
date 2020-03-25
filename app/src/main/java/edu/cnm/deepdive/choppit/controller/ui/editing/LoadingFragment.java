package edu.cnm.deepdive.choppit.controller.ui.editing;

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
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.List;

public class LoadingFragment extends Fragment {

  private MainViewModel viewModel;
  private static String url;
  private static String ingredient;
  private static String instruction;
  private TextView status;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    url = SelectionFragment.getUrl();
    ingredient = SelectionFragment.getIngredient();
    instruction = SelectionFragment.getInstruction();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    Log.d("LoadingFrag", "onCreateView - " + url + " " + ingredient + " " + instruction);
    View root = inflater.inflate(R.layout.fragment_loading, container, false);
    status = root.findViewById(R.id.status);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.resetData();
    viewModel.makeItGo(url);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

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
          break;
        case "gathering":
          status.setText(R.string.gathering);
          Log.d("LoadingFrag", "gathering");
          viewModel.processData(ingredient, instruction);
        break;
        case "processing":
          status.setText(R.string.processing);
          Log.d("LoadingFrag", "processing");
          viewModel.getSteps().observe(getActivity(), stepObserver);

      }
    }
  };

  final Observer<List<Step>> stepObserver = new Observer<List<Step>>() {
    @Override
    public void onChanged(List<Step> steps) {
      if (steps != null) {
        for (Step step : steps) {
          Log.d("LoadingFrag", step.toString());
        }
        ((MainActivity) getActivity()).navigateTo(R.id.navigation_editing);
      }
    }
  };
}
