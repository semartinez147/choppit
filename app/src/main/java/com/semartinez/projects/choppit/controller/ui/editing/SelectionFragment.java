package com.semartinez.projects.choppit.controller.ui.editing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.ui.home.HomeFragment;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
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
  private MainViewModel viewModel;
  private static String url;
  private static String ingredient = "";
  private static String instruction = "";
  private String html;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
  }

  // TODO pass url as Nav Arg
  public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.resetData();
    View root = inflater.inflate(R.layout.fragment_selection, container, false);
    setupWebView(root);
    HomeFragment homeFragment = new HomeFragment();
    url = (homeFragment.getUrl());
    ingredientInput = root.findViewById(R.id.ingredient_input);
    stepInput = root.findViewById(R.id.step_input);
    Button continueButton = root.findViewById(R.id.selection_extract);
    continueButton.setOnClickListener(v -> {
      instruction = stepInput.getText().toString();
      ingredient = ingredientInput.getText().toString();
      Navigation.findNavController(v).navigate(R.id.sel_load);
    });
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //    contentView.loadUrl(url);
    prepareHtml();
    contentView.loadData(html, "text/html", null);

    //  TODO disable for production
    ingredientInput.setText("1/2 pound elbow macaroni");
    stepInput.setText("oven to 350");

  }

  private void prepareHtml() {
    viewModel.makeItGo(url);
    viewModel.getStatus().observe(getViewLifecycleOwner(), s -> {
      if (s.equals("gathering")) {
        viewModel.generateHtml();
      }
    });
    viewModel.getHtml().observe(getViewLifecycleOwner(), h -> {
      contentView.loadData(String.valueOf(h), "text/html", null);
    });
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
    settings.setTextZoom(200);


    contentView.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        WebView.HitTestResult tap = ((WebView) v).getHitTestResult();

        Log.d("HitTest TAG", "getExtra = " + tap.getExtra() + ".  getType = " + tap.getType());
        return false;
      }
    });
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
