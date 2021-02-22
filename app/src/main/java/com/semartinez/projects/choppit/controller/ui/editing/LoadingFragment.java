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

public class LoadingFragment extends Fragment implements Observer<State> {

  private MainViewModel viewModel;
  private String url;
  private String ingredient;
  private String instruction;
  private TextView status;
  private boolean fromHome;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LoadingFragmentArgs args = LoadingFragmentArgs.fromBundle(requireArguments());
    url = args.getUrl();
    ingredient = args.getIngredient();
    instruction = args.getInstruction();
    fromHome = args.getFrom().equals("home");
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
    } else {
      status.setText(R.string.processing);
      viewModel.processData(ingredient, instruction);
    }
    observe();
  }

  private void observe() {
    viewModel.getStatus().observe(getViewLifecycleOwner(), this);
    viewModel.getThrowable().observe(getViewLifecycleOwner(), throwable -> {
      if (throwable != null) {
        Navigation.findNavController(requireView()).navigateUp();
      }
    });
  }

  @Override
  public void onChanged(State s) {
    switch (s) {
      case CONNECTING:
        Log.d("LoadingFrag", "connecting");
        status.setText(R.string.connecting);
        observe();
        break;
      case CONNECTED:
        status.setText(R.string.separating);
        break;
      case GENERATED:
        Navigation.findNavController(requireView()).navigate(LoadingFragmentDirections.loadSel());
        break;
      case FINISHING:
        status.setText(R.string.finishing);
        break;
      case FINISHED:
        Navigation.findNavController(requireView()).navigate(LoadingFragmentDirections.loadEdit());
      case READY:
      default:
        status.setText(R.string.warming_up);
        break;
    }
  }
}
