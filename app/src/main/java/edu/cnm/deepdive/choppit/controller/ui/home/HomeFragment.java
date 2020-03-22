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


public class HomeFragment extends Fragment {

  //  private MainViewModel viewModel;
  private View root;
  private EditText urlInput;
  static String url = "";
  private Button newRecipe;
  private Button myCookbook;

  public static HomeFragment createInstance() {
    HomeFragment fragment = new HomeFragment();
    Bundle args = new Bundle();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
    Log.d("HomeFragment", "created");
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
    // TODO debug ONLY
    urlInput.setText("https://www.foodnetwork.com/recipes/alton-brown/the-chewy-recipe-1909046");

    newRecipe.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        url = urlInput.getText().toString().trim();

        ((MainActivity) getActivity())
            .navigateTo(url.isEmpty() ? R.id.navigation_editing : R.id.navigation_selection);

      }
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