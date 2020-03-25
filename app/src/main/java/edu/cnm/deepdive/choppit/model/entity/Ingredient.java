package edu.cnm.deepdive.choppit.model.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.annotation.NonNull;

@Entity(
    indices = {
        @Index(value = "step_id")
    },
    foreignKeys = {
        @ForeignKey(
            entity = Step.class,
            parentColumns = "step_id",
            childColumns = "step_id",
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

  
  @ColumnInfo(name = "quantity")
  private String quantity;

  @ColumnInfo
  private String name;

  @NonNull
  @ColumnInfo(name = "unit", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
  private Unit unit;


  @ColumnInfo(name = "unit_alt", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
  private String unitAlt;


  public Ingredient() {

  }

  @Ignore
  public Ingredient(long stepId, long itemId, String quantity, Unit unit, String unitAlt, String name) {
    super();
    this.stepId = stepId;
    this.quantity = quantity;
    this.unit = unit;
    this.unitAlt = unitAlt;
    this.name = name;
  }

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

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public String getUnitAlt() {
    return unitAlt;
  }

  public void setUnitAlt(String unitAlt) {
    this.unitAlt = unitAlt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public enum Unit {
    DASH,
    TSP,
    TBSP,
    C,
    PT,
    QT,
    GAL,
    OZ,
    LB,
    OTHER; // Will generate a text field for things like "sprig" or "leg".

    @TypeConverter
    public static String toString(Unit value) {
      return (value != null) ? value.name() : null;

    }

    @TypeConverter
    public static Unit toUnit(
        String value) { // TODO make sure value is lowercase before it gets here.
      if (value == null) {
        return null;
      } else {
        try {
          return Unit.valueOf(value);
        } catch (IllegalArgumentException e) {
          switch (value) {
            case "teaspoon":
              return Unit.TSP;
            case "tablespoon":
              return Unit.TBSP;
            case "cup":
              return Unit.C;
            case "pint":
              return Unit.PT;
            case "quart":
              return Unit.QT;
            case "gallon":
              return Unit.GAL;
            case "ounce":
            case "fluid ounce":
              return Unit.OZ;
            case "pound":
              return Unit.LB;
            default:
              return Unit.OTHER;
          }
        }
      }
    }
  }
}
