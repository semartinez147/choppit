package com.semartinez.projects.choppit.controller.ui.editing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.ui.home.HomeFragment;
import com.semartinez.projects.choppit.viewmodel.MainViewModel;
import javax.annotation.Nonnull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * This fragment loads a {@link WebView} of the contents of the {@link HomeFragment} url. Below the
 * WebView, it loads two text entry fields.  The inputted {@link String}s are used by {@link Jsoup}
 * to process the {@link Document} generated from the HTML
 */
public class SelectionFragment extends Fragment {

  private static final String ENCODING = "UTF-8";
  private static final String MIME_TYPE = "text/html";
  private WebView contentView;
  private EditText ingredientInput;
  private EditText stepInput;
  private Document doc;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setRetainInstance(true);
  }

  @Override
  public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    MainViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    doc = viewModel.getDocument().getValue();
    viewModel.getDocument().removeObservers(getViewLifecycleOwner());
    View root = inflater.inflate(R.layout.fragment_selection, container, false);
    setupWebView(root);
    ingredientInput = root.findViewById(R.id.ingredient_input);
    stepInput = root.findViewById(R.id.step_input);
    Button continueButton = root.findViewById(R.id.selection_extract);
    continueButton.setOnClickListener(this::sendToLoading);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

        //FIXME: Not loading document
//    Jsoup.parseBodyFragment(doc.html());
    Log.e("LOOKIT", doc.outerHtml());
    contentView.loadData(doc.toString(), "text/html", ENCODING);

    //  TODO disable for production
    ingredientInput.setText("1/2 pound elbow macaroni");
    stepInput.setText("oven to 350");


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
//    settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    settings.setSupportZoom(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);
    settings.setLoadWithOverviewMode(true);
    settings.setUseWideViewPort(true);
//    settings.setBlockNetworkImage(true);
//    settings.setLoadsImagesAutomatically(false);
    settings.setTextZoom(300);
  }

  private void sendToLoading(View v) {
    SelectionFragmentDirections.SelLoad load = SelectionFragmentDirections.selLoad()
        .setIngredient(ingredientInput.getText().toString())
        .setInstruction(stepInput.getText().toString())
        .setFrom("sel");
    Navigation.findNavController(v).navigate(load);
  }

}
