package edu.cnm.deepdive.choppit.controller.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;

public class InfoFragment extends DialogFragment {

  private AlertDialog alert;
  private View dialogView;

  @SuppressLint("InflateParams")
  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_info, null);
    alert = new Builder(getContext())
        .setIcon(R.drawable.ic_help)
        .setTitle(R.string.default_title)
        .setView(dialogView)
        .setNeutralButton(R.string.info_ok, (dlg, which) -> {
        })
        .create();
    return alert;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return dialogView;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    MainViewModel viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    alert.setTitle("Active Fragment"); // TODO replace with active fragment
    TextView infoBody = dialogView.findViewById(R.id.info_body);
    String activeFragment = "";
    switch (activeFragment) { // TODO update all values below
      case ("HomeFragment"):
        infoBody.setText(R.string.home_fragment_info);
        break;
      case ("SelectionFragment"):
        infoBody.setText(R.string.selection_fragment_info);
        break;
      case ("EditingFragment"):
        infoBody.setText(R.string.editing_fragment_info);
        break;
      case ("RecipeFragment"):
        infoBody.setText(R.string.recipe_fragment_info);
        break;
    }
  }
}
