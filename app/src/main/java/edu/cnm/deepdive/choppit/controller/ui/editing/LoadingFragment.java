package edu.cnm.deepdive.choppit.controller.ui.editing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

    final Observer<List<Step>> stepObserver = new Observer<List<Step>>() {
      @Override
      public void onChanged(List<Step> steps) {
        if (steps != null){
        ((MainActivity) getActivity()).navigateTo(R.id.navigation_editing);
}      }
    };
    viewModel.getSteps().observe(this, stepObserver);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_loading, container, false);

    return root;
  }

}
