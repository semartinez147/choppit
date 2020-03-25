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
import androidx.lifecycle.Observer;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment;
import edu.cnm.deepdive.choppit.model.entity.Step;
import java.util.List;
import javax.annotation.Nonnull;

public class SelectionFragment extends Fragment {

  private WebView contentView;
  private EditText ingredientInput;
  private EditText stepInput;
  private static String url;
  private static String ingredient = "";
  private static String instruction = "";
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
    Log.d("Url from HomeFrag is", url);
    contentView.loadUrl(url);
    ingredientInput = root.findViewById(R.id.ingredient_input);
    stepInput = root.findViewById(R.id.step_input);

    // TODO debug ONLY
    ingredientInput.setText("bread flour");
    stepInput.setText("melt the butter");

    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(R.string.detail_selection);

    Button continueButton = root.findViewById(R.id.selection_continue);
    continueButton.setOnClickListener(new OnClickListener() {
      @SuppressLint("CheckResult")
      @Override
      public void onClick(View v) {
        Log.d("SEL", "onClick");
        instruction = stepInput.getText().toString();
        Log.d("SEL", "instruction text: " + instruction);
        ingredient = ingredientInput.getText().toString();

        ((MainActivity) getActivity()).navigateTo(R.id.navigation_loading);
      }
    });
    return root;
  }

  final Observer<List<Step>> viewModelObserver = steps -> {
    if (steps != null) {
      Log.d("SEL", "start navigation");
      ((MainActivity) getActivity()).navigateTo(R.id.navigation_loading);
    } else {
      ((MainActivity) getActivity()).showToast("Something went wrong!");
    }
  };



  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

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
//    settings.setBlockNetworkLoads(true);
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

  public static String getInstruction() {
    return instruction;
  }
}
