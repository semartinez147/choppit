package com.semartinez.projects.choppit.controller.ui.editing;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
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

/**
 * LoadingFragment takes over the display while the backend works.  It passes data to the {@link
 * MainViewModel}, then observes {@link MainViewModel#getStatus()} to display status messages in the
 * UI and navigate when the backend is ready.
 */
public class LoadingFragment extends Fragment {

  private MainViewModel viewModel;
  private String url;
  private TextView status;
  private boolean fromHome;


  /**
   * This override of onCreate retrieves Navigation arguments which include the url submitted by the
   * user and a boolean indicating whether the page navigating here was {@link
   * com.semartinez.projects.choppit.controller.ui.home.HomeFragment} or not (which determines what
   * processing methods are called next).
   */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LoadingFragmentArgs args = LoadingFragmentArgs.fromBundle(requireArguments());
    url = args.getUrl();
    fromHome = args.getFromHome();
  }

  /**
   * This override of onCreateView inflates the Fragment using findViewById(), retrieves a reference
   * to the {@link MainViewModel} and starts the loading animation.
   */
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

  /**
   * This override of onViewCreated clears data from the MainViewModel and calls the first method in
   * the data processing flow if Loading was navigated to from the Home Fragment. It also attaches
   * an observer to the MainViewModel fields that indicate progress in the backend.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (fromHome) {
      viewModel.resetData();
      viewModel.makeItGo(url);
    } else {
    }
    observe();
  }

  private void observe() {
    StateObserver observer = new StateObserver();
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
          Navigation.findNavController(requireView())
              .navigate(LoadingFragmentDirections.loadEdit());
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
