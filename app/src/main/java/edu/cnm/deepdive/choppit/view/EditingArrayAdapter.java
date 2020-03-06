package edu.cnm.deepdive.choppit.view;

import android.content.Context;
import android.widget.ArrayAdapter;
import edu.cnm.deepdive.choppit.R;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import java.util.List;

public class EditingArrayAdapter extends ArrayAdapter {

  private final Context context;
  private final List<Ingredient> ingredients;

  public EditingArrayAdapter(Context context,
      List<Ingredient> ingredients) {
    super(context, R.layout.edit_ingredient_list_item);
    this.context = context;
    this.ingredients = ingredients;
  }

}