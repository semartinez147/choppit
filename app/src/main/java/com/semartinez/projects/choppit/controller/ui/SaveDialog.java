package com.semartinez.projects.choppit.controller.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
  private EditText title;

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
        .setIcon(R.drawable.ic_edit) // TODO: Make a save icon
        .setTitle("Save " + recipe.getTitle() + "?")
        .setView(dialogView)
        //TODO: Save methods not working correctly.  Try multichoicelistener?
        .setNegativeButton("SAVE NEW", listener)
        .setNeutralButton("CANCEL", listener)
        .setPositiveButton("OVERWRITE", listener)
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

OnClickListener listener = new OnClickListener() {
  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
      case -1: { //overwrite
        recipe.setTitle(title.toString());
        viewModel.updateRecipe(recipe);
        break;
      }
      case -2: { //save new
        if (recipe.getTitle().equalsIgnoreCase(title.toString())) {
          errorToast();
        } else {
          recipe.setRecipeId(0);
          viewModel.saveRecipe(recipe);
        }
        break;
      }
      case -3: {
      }
    }
    dismiss();// neutral
  }
};

  private void errorToast() {
    Toast toast = Toast.makeText(getContext(), "Can't save a new recipe with the same title.", Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0,
        getResources().getDimensionPixelOffset(R.dimen.toast_vertical_margin));
    toast.show();
  }
}
