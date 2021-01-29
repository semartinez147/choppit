package com.semartinez.projects.choppit.controller.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import com.semartinez.projects.choppit.controller.ui.editing.SelectionFragment;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;

public class SelectDialog extends DialogFragment implements OnClickListener {

  private View dialogView;
  private final SelectionFragment fragment;
  private final String selection;
  private final MainViewModel viewModel;
  private TextView selectText;

  public SelectDialog(String selection, SelectionFragment fragment,
      MainViewModel viewModel) {
    this.fragment = fragment;
    this.selection = selection;
    this.viewModel = viewModel;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    assert getContext() != null;
    dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_select, null);
    return new Builder(getContext())
        .setIcon(R.drawable.ic_copy)
        .setTitle("Copy recipe text")
        .setView(dialogView)
        .setPositiveButton("INSTRUCTION", this)
        .setNegativeButton("INGREDIENT", this)
        .setNeutralButton("NO", this)
        .create();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    selectText = dialogView.findViewById(R.id.select_text);
    selectText.setText(selection);
    return dialogView;
  }

  // TODO implement call to SelectionFragment.mark_ methods
  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
      case -1: { //positive button


        break;
      }
      case -2: { //negative button


        break;
      }
      case -3:
      default: { //neutral button
      }
    }
    dismiss();

  }
}
