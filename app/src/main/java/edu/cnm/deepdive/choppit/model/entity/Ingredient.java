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
      @Index(value = "item_id"),
      @Index(value = "item_name")
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

  @ColumnInfo(name = "step_id", index = true)
  private long stepId;

  @ColumnInfo(name = "item_id")
  private long itemId;

  @ColumnInfo(name = "item_name", collate = ColumnInfo.NOCASE)
  private String name;

  @ColumnInfo(name = "item_quantity")
  private long quantity;

  @ColumnInfo(name = "item_unit", typeAffinity = ColumnInfo.TEXT)
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
    TBSP,
    CUP,
    PINT,
    QUART,
    GALLON,
    OZ,
    LB,
    OTHER; // Will generate a text field for things like "sprig" or "leg".

    @TypeConverter
    public static Integer toInteger(Unit value) {
      return (value != null) ? value.ordinal() : null;
    }

    @TypeConverter
    public static Unit toUnit(Integer value) {
      return (value != null) ? Unit.values()[value] : null;
    }

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
