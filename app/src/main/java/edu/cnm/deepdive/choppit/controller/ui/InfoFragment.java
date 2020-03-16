package edu.cnm.deepdive.choppit.controller.ui;

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
import edu.cnm.deepdive.choppit.R;

public class InfoFragment extends DialogFragment {

  private AlertDialog alert;
  private View dialogView;
  private int currentLocation;
  private String fragLabel;

  public InfoFragment(int navFragment, String fragLabel) {
    currentLocation = navFragment;
    this.fragLabel = fragLabel;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_info, null);
    alert = new Builder(getContext())
        .setIcon(R.drawable.ic_help)
        .setTitle(fragLabel + " Information")
        .setView(dialogView)
        .setNeutralButton("OK", (dlg, which) -> {})
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

    TextView info = dialogView.findViewById(R.id.info);

    switch(currentLocation) {
      case R.id.navigation_home:
      info.setText(R.string.home_info);
      break;
      case R.id.navigation_selection:
      info.setText(R.string.selection_info);
      break;
      case R.id.navigation_editing:
      info.setText(R.string.editing_info);
      break;
      case R.id.navigation_cookbook:
      info.setText(R.string.cookbook_info);
      break;
      default:
      info.setText(R.string.default_info);
      break;
    }
  }
}
