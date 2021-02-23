package com.semartinez.projects.choppit.controller.ui.editing;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import com.semartinez.projects.choppit.viewmodel.MainViewModel.State;

public class LoadingFragment extends Fragment {

  private MainViewModel viewModel;
  private String url;
  private TextView status;
  private boolean fromHome;
  private StateObserver observer;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LoadingFragmentArgs args = LoadingFragmentArgs.fromBundle(requireArguments());
    url = args.getUrl();
    fromHome = args.getFromHome();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_loading, container, false);
    status = root.findViewById(R.id.status);
    viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    ImageView spinner = root.findViewById(R.id.load_logo);
    AnimatedVectorDrawable d = (AnimatedVectorDrawable) spinner.getDrawable();

    d.start();
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (fromHome) {
      viewModel.resetData();
      viewModel.makeItGo(url);
    }
    else {
    Log.d("LoadingFlow", "not from home");
    }
    observe();
  }

  private void observe() {
    observer = new StateObserver();
    viewModel.getStatus().observe(getViewLifecycleOwner(), observer);
    viewModel.getThrowable().observe(getViewLifecycleOwner(), throwable -> {
      if (throwable != null) {
        Navigation.findNavController(requireView()).navigateUp();
      }
    });
  }

  private class StateObserver implements Observer<State> {

    @Override
    public void onChanged(State s) {
      switch (s) {
        case CONNECTING:
          Log.d("LoadingFrag", "connecting");
          status.setText(R.string.connecting);
          break;
        case CONNECTED:
          status.setText(R.string.separating);
          break;
        case GENERATED:
          Navigation.findNavController(requireView()).navigate(LoadingFragmentDirections.loadSel());
          break;
        case PROCESSING:
          status.setText(R.string.processing);
          break;
        case FINISHING:
          status.setText(R.string.finishing);
          break;
        case FINISHED:
          Navigation.findNavController(requireView()).navigate(LoadingFragmentDirections.loadEdit());
          break;
        case READY:
          if (!fromHome) {
            break;
          }
        default:
          status.setText(R.string.warming_up);
          break;
      }
    }
  }
}
