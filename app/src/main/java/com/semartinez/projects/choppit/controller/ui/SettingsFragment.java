package com.semartinez.projects.choppit.controller.ui;

import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import com.semartinez.projects.choppit.R;
import com.semartinez.projects.choppit.controller.MainActivity;

public class SettingsFragment extends Fragment implements OnItemSelectedListener {

  private String typefaceSelection;
  private TextView title;
  private TextView body;

  @Nullable
  @org.jetbrains.annotations.Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_settings, container, false);

    title =root.findViewById(R.id.settings_title);
    body = root.findViewById(R.id.settings_body);
    Spinner fontChoice = root.findViewById(R.id.settings_spinner);

    ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
        .createFromResource(getContext(), R.array.font_names_array, 17367048);


    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    fontChoice.setAdapter(arrayAdapter);
    fontChoice.setOnItemSelectedListener(this);
    Button okay = root.findViewById(R.id.okay_button);
    okay.setOnClickListener(v -> {
      Intent intent = new Intent(getContext(), MainActivity.class);
      startActivity(intent);
    });

    return root;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    typefaceSelection = ((String) parent.getSelectedItem());

    int fontId = 0;
    switch (typefaceSelection) {
      case "Lato":
        fontId = R.font.lato;
        break;
      case "Droid Sans":
        fontId = R.font.droid;
        break;
      case "Quicksand":
        fontId = R.font.quicksand;
        break;
      case "Lora":
        fontId = R.font.lora;
        break;
      case "Roboto Slab":
        fontId = R.font.roboto;
        break;
    }

    try {
      title.setTypeface(ResourcesCompat.getFont(requireContext(), fontId));
      body.setTypeface(ResourcesCompat.getFont(requireContext(), fontId));
    } catch (NotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    // do nothing
  }

}
