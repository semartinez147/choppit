package edu.cnm.deepdive.choppit.controller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.editing.SelectionFragment;
import java.util.Objects;


public class HomeFragment extends Fragment {

  //  private MainViewModel viewModel;
  private View view;
  private HomeViewModel homeViewModel;
  private EditText urlInput;

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

    newRecipe.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity())
            .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new SelectionFragment());
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.commit();
      }
    });

    Button myCookbook = (Button) root.findViewById(R.id.my_cookbook);

    myCookbook.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast toast = new Toast(getContext());
        Toast.makeText(getContext(), "Under construction, try again later", Toast.LENGTH_LONG)
            .show();
      }
    });

    return root;
  }
}