package edu.cnm.deepdive.choppit.controller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.cookbook.CookbookFragment;
import edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment;
import edu.cnm.deepdive.choppit.controller.ui.editing.SelectionFragment;
import java.util.Objects;


public class HomeFragment extends Fragment {

  //  private MainViewModel viewModel;
  private View root;
  private EditText urlInput;
  private static String url = "";
  private Button newRecipe;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    root = inflater.inflate(R.layout.fragment_home, container, false);
    urlInput = (EditText) root.findViewById(R.id.url_input);
//      urlInput.setText("https://www.foodnetwork.com/recipes/alton-brown/the-chewy-recipe-1909046");
    newRecipe = (Button) root.findViewById(R.id.new_recipe);

    newRecipe.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        url = urlInput.getText().toString();

        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity())
            .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, (url.equals("\\s*") || url.isEmpty()) ?
            new EditingFragment() : new SelectionFragment());
        fragmentTransaction.addToBackStack("homeFragment");
        fragmentTransaction.commit();

      }
    });

    Button myCookbook = (Button) root.findViewById(R.id.my_cookbook);

    myCookbook.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity())
            .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new CookbookFragment());
        fragmentTransaction.addToBackStack("cookbookFragment");
        fragmentTransaction.commit();
      }
    });

    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("url", urlInput.getText().toString());
  }

  public String getUrl() {
    return url;
  }
}