package edu.cnm.deepdive.choppit.controller.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.textfield.TextInputLayout;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;


public class HomeFragment extends Fragment {

//  private MainViewModel viewModel;
  private View view;
  private HomeViewModel homeViewModel;
  private ImageView logo;
  private TextInputLayout urlInput;
  private Button newRecipe;
  private Button cookbook;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    return root;
  }

}