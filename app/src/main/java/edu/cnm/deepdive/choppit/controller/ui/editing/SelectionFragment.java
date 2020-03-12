package edu.cnm.deepdive.choppit.controller.ui.editing;

import android.annotation.SuppressLint;
import android.drm.DrmStore.Action;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.controller.ui.home.HomeFragment;
import java.util.Objects;
import javax.annotation.Nonnull;

public class SelectionFragment extends Fragment {

  private WebView contentView;
  private String url;
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
    url = (homeFragment.getUrlInput());
//    url = "https://www.foodnetwork.com/recipes/alton-brown/the-chewy-recipe-1909046";
    contentView.loadUrl(url);

    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    Button continue_button = (Button) root.findViewById(R.id.selection_continue);
    continue_button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity())
            .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new EditingFragment());
        fragmentTransaction.addToBackStack("SelectionFragment");
        fragmentTransaction.commit();
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
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
    }
    return super.onOptionsItemSelected(item);
  }

}
