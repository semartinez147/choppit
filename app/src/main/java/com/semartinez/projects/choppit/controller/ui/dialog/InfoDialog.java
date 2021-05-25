package com.semartinez.projects.choppit.controller.ui.dialog;

import static com.semartinez.projects.choppit.service.Constants.NAV_COOK;
import static com.semartinez.projects.choppit.service.Constants.NAV_EDIT;
import static com.semartinez.projects.choppit.service.Constants.NAV_HOME;
import static com.semartinez.projects.choppit.service.Constants.NAV_LOAD;
import static com.semartinez.projects.choppit.service.Constants.NAV_REC;
import static com.semartinez.projects.choppit.service.Constants.NAV_SEL;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import com.semartinez.projects.choppit.R;

/**
 * InfoDialog displays some help text depending on the active fragment when it's opened.
 */
public class InfoDialog extends DialogFragment {

  private View dialogView;
  private final int currentLocation;
  private final String fragLabel;

  /**
   * @param navFragment is the Switch input, taken from the active Fragment id when the Help button
   *                    is pressed.
   * @param fragLabel   is populated from the navFragment and displayed in the Action Bar.
   */
  public InfoDialog(int navFragment, String fragLabel) {
    currentLocation = navFragment;
    this.fragLabel = fragLabel;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    assert getContext() != null;
    dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_info, null);
    return new Builder(getContext())
        .setIcon(R.drawable.ic_help)
        .setTitle(fragLabel + " Information")
        .setView(dialogView)
        .setNeutralButton("OK", (dlg, which) -> {
        })
        .create();
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

    TextView info = dialogView.findViewById(R.id.dialog_info);

    switch (currentLocation) {
      case NAV_HOME:
        info.setText(R.string.home_info);
        break;
      case NAV_SEL:
        info.setText(R.string.selection_info);
        break;
      case NAV_LOAD:
        info.setText(R.string.loading_info);
        break;
      case NAV_EDIT:
        info.setText(R.string.editing_info);
        break;
      case NAV_COOK:
        info.setText(R.string.cookbook_info);
        break;
      case NAV_REC:
        info.setText(R.string.recipe_info);
        break;
      default:
        info.setText(R.string.default_info);
        break;
    }
  }
}
