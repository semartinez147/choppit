package com.semartinez.projects.choppit.controller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;

/**
 * This fragment is the first to load.  The New Recipe {@link Button} navigates to {@link
 * com.semartinez.projects.choppit.controller.ui.editing.SelectionFragment} if a url has been
 * entered, or to {@link com.semartinez.projects.choppit.controller.ui.editing.EditingFragment} if
 * not.
 *
 * @author Samuel Martinez
 */
public class HomeFragment extends Fragment {

  private EditText urlInput;
  private String url = "";
  private Button newRecipe;
  private Button myCookbook;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);

  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    urlInput = root.findViewById(R.id.url_input);
    newRecipe = root.findViewById(R.id.new_recipe);
    myCookbook = root.findViewById(R.id.my_cookbook);
    assert getActivity() != null;
    MainViewModel viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.resetData();
    viewModel.resetStatus();
    urlInput.setText(viewModel.getSharedUrl());
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    myCookbook.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.home_cook));

    // DEV pre-fill url for testing if no url is shared
    if (urlInput.getText().toString().length() < 1) {
      // urlInput.setText("https://www.foodnetwork.com/recipes/alton-brown/the-chewy-recipe-1909046");
      urlInput.setText(
          "https://www.foodnetwork.com/recipes/alton-brown/baked-macaroni-and-cheese-recipe-1939524");
    }
    newRecipe.setOnClickListener(v -> {
      url = urlInput.getText().toString().trim();
      HomeFragmentDirections.HomeLoad load = HomeFragmentDirections.homeLoad()
          .setFromHome(true)
          .setUrl(urlInput.getText().toString().trim());
      Navigation.findNavController(v)
          .navigate(url.isEmpty() ? HomeFragmentDirections.homeEdit() : load);

    });

  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("url", urlInput.getText().toString());
  }

}