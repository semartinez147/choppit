package edu.cnm.deepdive.choppit.controller.ui.editing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.MainActivity;
import edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment;
import javax.annotation.Nonnull;


/**
 * This fragment loads a {@link WebView} of the contents of {@link HomeFragment#getUrl()}. Below the
 * WebView, it loads two text entry fields.  The inputted {@link String}s are used by {@link
 * org.jsoup.Jsoup} to process the {@link org.jsoup.nodes.Document} generated from the HTML
 */
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
    contentView.loadUrl(url);
    ingredientInput = root.findViewById(R.id.ingredient_input);
    stepInput = root.findViewById(R.id.step_input);

//  TODO disable for production
    ingredientInput.setText("bread flour");
    stepInput.setText("melt the butter");

    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(R.string.detail_selection);

    Button continueButton = root.findViewById(R.id.button);
    continueButton.setOnClickListener(new OnClickListener() {
      @SuppressLint("CheckResult")
      @Override
      public void onClick(View v) {
        instruction = stepInput.getText().toString();
        ingredient = ingredientInput.getText().toString();
        ((MainActivity) getActivity()).navigateTo(R.id.navigation_loading);
      }
    });
    return root;
  }

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

    // TODO is there a setting that loads only text & formatting?
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
//    settings.setBlockNetworkLoads(true);  this setting is too restrictive and breaks the webview.
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
