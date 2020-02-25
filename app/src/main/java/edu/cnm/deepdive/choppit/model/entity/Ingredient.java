package edu.cnm.deepdive.choppit.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

@Entity(
    indices = {
      @Index(value = "step_id"),
      @Index(value = "item_id")
    },
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
        )
    }
)
public class Ingredient {

  @ColumnInfo(name = "ingredient_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "step_id")
  private long stepId;

  @ColumnInfo(name = "item_id")
  private long itemId;

  @ColumnInfo(name = "quantity")
  private long quantity;

  @ColumnInfo(name = "unit", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
  private Unit unit;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public long getQuantity() {
    return quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public enum Unit {
    DASH,
    TSP,
    TEASPOON,
    TBSP,
    TABLESPOON,
    C,
    CUP,
    PT,
    PINT,
    QT,
    QUART,
    GAL,
    GALLON,
    OZ,
    OUNCE,
    LB,
    POUND,
    OTHER; // Will generate a text field for things like "sprig" or "leg".

    @TypeConverter
    public static String toString(Unit value) {
      return (value != null) ? value.name() : null;

    }
    @TypeConverter
    public static Unit toUnit(String value) {
      return (value != null) ? Unit.valueOf(value) : null;
    }
  }
}
