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

public class SelectDialog extends DialogFragment implements OnClickListener {

  private View dialogView;
  private final SelectionFragment fragment;
  private final String selection;

  public SelectDialog(String selection, SelectionFragment fragment) {
    this.fragment = fragment;
    this.selection = selection;
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
        .setNegativeButton("NO", this)
        .setNeutralButton("INGREDIENT", this)
        .create();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    TextView selectText = dialogView.findViewById(R.id.select_text);
    selectText.setText(selection);
    return dialogView;
  }

  // TODO implement call to SelectionFragment.mark_ methods
  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
      case -1: {
        fragment.markAsStep(selection);
        break;
      }
      case -3: {
        fragment.markAsIngredient(selection);
        break;
      }
      case -2:
      default: {
      }
    }
    dismiss();

  }
}
