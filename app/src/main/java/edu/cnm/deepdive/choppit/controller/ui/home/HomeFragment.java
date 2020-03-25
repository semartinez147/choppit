package edu.cnm.deepdive.choppit.controller.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;

/**
 * This fragment is the first to load.  The New Recipe {@link Button} navigates to {@link
 * edu.cnm.deepdive.choppit.controller.ui.editing.SelectionFragment} if a url has been entered, or
 * to {@link edu.cnm.deepdive.choppit.controller.ui.editing.EditingFragment} if not.
 *
 * @author Samuel Martinez
 */
public class HomeFragment extends Fragment {

  private View root;
  private EditText urlInput;
  static String url = "";
  private Button newRecipe;
  private Button myCookbook;

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
    newRecipe = (Button) root.findViewById(R.id.new_recipe);
    myCookbook = (Button) root.findViewById(R.id.my_cookbook);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(false);
    myCookbook.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ((MainActivity) getActivity()).navigateTo(R.id.navigation_cookbook);
      }
    });
    // TODO disable for production
    urlInput.setText("https://www.foodnetwork.com/recipes/alton-brown/the-chewy-recipe-1909046");

    newRecipe.setOnClickListener(v -> {
      url = urlInput.getText().toString().trim();

      ((MainActivity) getActivity())
          .navigateTo(url.isEmpty() ? R.id.navigation_editing : R.id.navigation_selection);

    });

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