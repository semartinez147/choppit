package edu.cnm.deepdive.choppit.controller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import edu.cnm.deepdive.choppit.R;


public class HomeFragment extends Fragment {

  private View view;
  private HomeViewModel homeViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    homeViewModel =
        ViewModelProviders.of(this).get(HomeViewModel.class);
    view = inflater.inflate(R.layout.fragment_home, container, false);
//    final TextView textView = root.findViewById(R.id.text_home);
    return view;
  }

}