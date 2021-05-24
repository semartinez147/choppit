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

  /**
   * This override of onCreateView clears all data from the {@link MainViewModel} and inflates the
   * Fragment using findViewById().
   */
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    MainViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    viewModel.resetData();
    viewModel.resetStatus();
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    urlInput = root.findViewById(R.id.url_input);
    newRecipe = root.findViewById(R.id.new_recipe);
    myCookbook = root.findViewById(R.id.my_cookbook);

    urlInput.setText(viewModel.getSharedUrl());
    return root;
  }

  /**
   * This override of onViewCreated attaches onClickListeners to the two UI buttons and prepares to
   * navigate based on the user's actions.  If there is a url in the urlInput field, the New Recipe
   * button navigates to the {@link com.semartinez.projects.choppit.controller.ui.editing.LoadingFragment}
   * where processing begins. Otherwise, it navigates to the {@link com.semartinez.projects.choppit.controller.ui.editing.EditingFragment}
   * with blank fields to manually input a recipe.  My Cookbook navigates to the {@link
   * com.semartinez.projects.choppit.controller.ui.cookbook.CookbookFragment}.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    myCookbook.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.home_cook));
    newRecipe.setOnClickListener(v -> {
      url = urlInput.getText().toString().trim();
      HomeFragmentDirections.HomeLoad load = HomeFragmentDirections.homeLoad()
          .setFromHome(true)
          .setUrl(urlInput.getText().toString().trim());
      Navigation.findNavController(v)
          .navigate(url.isEmpty() ? HomeFragmentDirections.homeEdit() : load);

    });
    // DEV pre-fill url for testing if no url is shared
    prefill();
  }


  private void prefill() {
    if (urlInput.getText().toString().length() < 1) {
      // urlInput.setText("https://www.foodnetwork.com/recipes/alton-brown/the-chewy-recipe-1909046");
      urlInput.setText(
          "https://www.foodnetwork.com/recipes/alton-brown/baked-macaroni-and-cheese-recipe-1939524");
    }
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    try {
      outState.putString("url", urlInput.getText().toString());
    } catch (NullPointerException e) {
      outState.putString("url", "");
    }
  }

}