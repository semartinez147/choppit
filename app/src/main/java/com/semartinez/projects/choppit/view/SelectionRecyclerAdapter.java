package com.semartinez.projects.choppit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.semartinez.projects.choppit.controller.ui.editing.SelectionFragment;
import com.semartinez.projects.choppit.databinding.ItemSelectionListBinding;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class SelectionRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final Context context;
  private final String[] strings;
  private final SelectionFragment selectionFragment;

  public SelectionRecyclerAdapter(Context context, Set<String> strings,
      SelectionFragment selectionFragment) {
    this.context = context;
    this.strings = strings.toArray(new String[0]);
    this.selectionFragment = selectionFragment;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    ItemSelectionListBinding selectionListItemBinding = ItemSelectionListBinding
        .inflate(layoutInflater, parent, false);
    return new SelectionViewHolder(selectionListItemBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull ViewHolder viewHolder, int position) {
    String string = strings[position];
    ((SelectionViewHolder) viewHolder).bind(string, selectionFragment);
  }

  @Override
  public int getItemCount() {
    return strings.length;
  }

  public String[] getStrings() {
    return strings;
  }

  static class SelectionViewHolder extends RecyclerView.ViewHolder {

    private final ItemSelectionListBinding binding;

    public SelectionViewHolder(ItemSelectionListBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(String string, SelectionFragment selectionFragment) {
      binding.setUiController(selectionFragment);
      binding.setString(string);
      binding.executePendingBindings();
    }
  }
}