package edu.cnm.deepdive.choppit.controller.ui.editing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.util.Objects;
import javax.annotation.Nonnull;

public class SelectionFragment extends Fragment {

  private MainViewModel viewModel;
  private WebView contentView;
  private static String url;
  private  EditText ingredientInput;
  private  EditText stepInput;
  private static String ingredient = "";
  private static String step = "";
  private HomeFragment homeFragment;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
  }

  public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_selection, container, false);
    setupWebView(root);
    homeFragment = new HomeFragment();
    url = (homeFragment.getUrl());
    Log.d("Url from HomeFrag is: ", url);
    contentView.loadUrl(url);

    ingredientInput = root.findViewById(R.id.ingredient_input);
    stepInput = root.findViewById(R.id.step_input);

    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);


    Button continueButton = (Button) root.findViewById(R.id.selection_continue);
    continueButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        step = stepInput.getText().toString();
        ingredient = ingredientInput.getText().toString();

        ((MainActivity)getActivity()).navigateTo(R.id.navigation_editing);
      }
    });

    return root;
  }



  @SuppressLint("SetJavaScriptEnabled")
  private void setupWebView(View root) {
    contentView = root.findViewById(R.id.selection_view);
    contentView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return false;
      }
    });
    WebSettings settings = contentView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    settings.setSupportZoom(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);
    settings.setLoadWithOverviewMode(true);
    settings.setUseWideViewPort(true);
    settings.setBlockNetworkImage(true);
    settings.setLoadsImagesAutomatically(false);
    settings.setBlockNetworkLoads(true);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
    }
    return super.onOptionsItemSelected(item);
  }

  public static String getUrl() {
    return url;
  }

  public static String getIngredient() {
    return ingredient;
  }

  public static String getStep() {
    return step;
  }
}
