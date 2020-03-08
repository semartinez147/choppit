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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.textfield.TextInputLayout;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;


public class HomeFragment extends Fragment implements View.OnClickListener{

//  private MainViewModel viewModel;
  private View view;
  private HomeViewModel homeViewModel;
  private TextInputLayout urlInput;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);

    Button newRecipe = (Button) root.findViewById(R.id.new_recipe);
    Button myCookbook = (Button) root.findViewById(R.id.my_cookbook);
    return root;
  }


  @Override
  public void onClick(View v) {
    switch (view.getId()) {
      case (R.id.new_recipe):
        FragmentTransaction fragmentTransaction = getActivity()
            .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_home, new EditingFragment());
        break;
      case (R.id.my_cookbook):

    }
  }
}