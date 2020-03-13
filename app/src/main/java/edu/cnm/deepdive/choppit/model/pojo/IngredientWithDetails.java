package edu.cnm.deepdive.choppit.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Item;
import java.util.List;
import javax.annotation.Nonnull;

public class IngredientWithDetails {

  public IngredientWithDetails() {

  }

  @Nonnull
  @Embedded
  private Ingredient ingredient;

  @Relation(parentColumn = "id", entityColumn = "ingredient_id", entity = Item.class)
  private Item item;

  @Nonnull
  public Ingredient getIngredient() {
    return ingredient;
  }

  public void setIngredient(@Nonnull Ingredient ingredient) {
    this.ingredient = ingredient;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }
}
