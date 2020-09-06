package com.semartinez.projects.choppit.controller.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.ui.editing.EditingFragment;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import org.jetbrains.annotations.NotNull;

public class SaveDialog extends DialogFragment {

  private View dialogView;
  private final Recipe recipe;
  private final View view;
  private final MainViewModel viewModel;
  private TextView title;

  public SaveDialog(Recipe recipe, View view, MainViewModel viewModel) {
    this.recipe = recipe;
    this.view = view;
    this.viewModel = viewModel;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    assert getContext() != null;
    dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_save, null);
    return new Builder(getContext())
        .setIcon(R.drawable.ic_remove)
        .setTitle("Save " + recipe.getTitle() + "?")
        .setView(dialogView)
        //TODO: Save methods not working correctly.  Try multichoicelistener?
        .setNegativeButton("SAVE NEW", (dlg, which) -> {
          if (title.toString().equalsIgnoreCase(recipe.getTitle())) {
            errorToast();
          } else {
            viewModel.saveRecipe(recipe);
          }
        })
        .setNeutralButton("CANCEL", (dlg, which) -> {

        })
        .setPositiveButton("OVERWRITE", (dlg, which) -> {
          if (title.toString().equals(recipe.getTitle())) {
            viewModel.updateRecipe(recipe);
          }
        })
        .create();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    title = dialogView.findViewById(R.id.save_title);
    title.setText(recipe.getTitle());
    return dialogView;
  }

  @Override
  public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
    super.onDismiss(dialog);
    ((EditingFragment)getParentFragment()).navAfterSave(view);
  }

  private void errorToast() {
    Toast toast = Toast.makeText(getContext(), "Can't save a new recipe with the same title.", Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0,
        getResources().getDimensionPixelOffset(R.dimen.toast_vertical_margin));
    toast.show();
  }
}
