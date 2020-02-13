package edu.cnm.deepdive.choppit.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

@Entity(
    // FIXME fix indices
//    indices = {
//      @Index(value = "item_id, item_name")
//    },
    foreignKeys = {
        @ForeignKey(
            entity = Step.class,
            parentColumns = "step_id",
            childColumns = "step_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Item.class,
            parentColumns = "item_id",
            childColumns = "item_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Item.class,
            parentColumns = "item_name",
            childColumns = "item_name",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Ingredient {

  @ColumnInfo(name = "ingredient_id")
  @PrimaryKey(autoGenerate = true)
  private long ingredientId;

  @ColumnInfo(name = "step_id", index = true)
  private long stepId;

  @ColumnInfo(name = "item_id")
  private long itemId;

  @ColumnInfo(name = "item_name")
  private String itemName;

  @ColumnInfo(name = "item_quantity")
  private long itemQuantity;

  @ColumnInfo(name = "item_unit")
  private ItemUnit itemUnit;

  public long getIngredientId() {
    return ingredientId;
  }

  public void setIngredientId(long ingredientId) {
    this.ingredientId = ingredientId;
  }

  public long getStepId() {
    return stepId;
  }

  public void setStepId(long stepId) {
    this.stepId = stepId;
  }

  public long getItemId() {
    return itemId;
  }

  public void setItemId(long itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public long getItemQuantity() {
    return itemQuantity;
  }

  public void setItemQuantity(long itemQuantity) {
    this.itemQuantity = itemQuantity;
  }

  public ItemUnit getItemUnit() {
    return itemUnit;
  }

  public void setItemUnit(ItemUnit itemUnit) {
    this.itemUnit = itemUnit;
  }

  public enum ItemUnit {
    DASH,
    TSP,
    TBSP,
    CUP,
    PINT,
    QUART,
    GALLON,
    OZ,
    LB;

    @TypeConverter
    public static Integer toInteger(ItemUnit value) {
      return (value != null) ? value.ordinal() : null;
    }

    @TypeConverter
    public static ItemUnit toItemUnit(Integer value) {
      return (value != null) ? ItemUnit.values()[value] : null;
    }
  }
}
