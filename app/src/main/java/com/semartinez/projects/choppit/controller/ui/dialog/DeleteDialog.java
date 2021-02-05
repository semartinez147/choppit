package com.semartinez.projects.choppit.controller.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;

public class DeleteDialog extends DialogFragment {

  private View dialogView;
  private final Recipe recipe;
  private final MainViewModel viewModel;

  public DeleteDialog(Recipe recipe, MainViewModel viewModel) {
    this.recipe = recipe;
    this.viewModel = viewModel;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    assert getContext() != null;
    dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_delete, null);
    return new Builder(getContext())
        .setIcon(R.drawable.ic_remove)
        .setTitle("Delete " + recipe.getTitle() + "?")
        .setView(dialogView)
        .setNegativeButton("DELETE", (dlg, which) -> viewModel.deleteRecipe(recipe))
        .setNeutralButton("CANCEL", (dlg, which) -> {})
        .create();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return dialogView;
  }
}
