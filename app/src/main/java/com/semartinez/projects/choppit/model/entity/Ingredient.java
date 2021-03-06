package com.semartinez.projects.choppit.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import java.util.Objects;

@Entity(
    indices = {
        @Index(value = "recipe_id")
    },
    foreignKeys = {
        @ForeignKey(
            entity = Recipe.class,
            parentColumns = "recipe_id",
            childColumns = "recipe_id",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    }
)
public class Ingredient {

  @ColumnInfo(name = "ingredient_id")
  @PrimaryKey(autoGenerate = true)
  private long id;


  @ColumnInfo(name = "recipe_id")
  private long recipeId;


  @ColumnInfo(name = "quantity")
  private String quantity;

  @ColumnInfo
  private String name;

  @ColumnInfo(name = "unit", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
  private Unit unit;


  @ColumnInfo(name = "unit_alt", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
  private String unitAlt;


  /**
   * Ingredients belong to {@link Step}s, which belong to {@link Recipe}s.  each Ingredient consists
   * of a {@link #quantity} String, a {@link #unit} {@link Unit}, and a {@link #name} String.  Unit
   * is primarily an {@link Enum} of abbreviated cooking terms, but also allows the value {@link
   * Unit#OTHER} with {@link #unitAlt} as a String stand-in for less-common terms (i.e. sprig) and
   * items measured whole (i.e. eggs)
   */
  public Ingredient() {

  }

  @Ignore
  public Ingredient(long recipeId, String quantity, Unit unit, String unitAlt,
      String name) {
    super();
    this.recipeId = recipeId;
    this.quantity = quantity;
    this.unit = unit;
    this.unitAlt = (unitAlt != null && !unitAlt.isEmpty()) ? unitAlt : unit.toString();
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(long recipeId) {
    this.recipeId = recipeId;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    // TODO: Remove anything that isn't a number, space or "/"

    this.quantity = quantity;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
    unitAlt = this.unit.toString();
  }

  public String getUnitText() {
    return unitAlt.equalsIgnoreCase("other")? " " : unitAlt;
  }

  public void setUnitText(String unit) {
    unitAlt = unit;
    this.unit = Unit.toUnit(unit != null? unit : "other");
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
    public static Unit toUnit(String value) {
      if (value == null) {
        return Unit.OTHER;
      } else {
        try {
          return Unit.valueOf(value);
        } catch (IllegalArgumentException e) {
          switch (value) {
            case "teaspoon":
            case "teaspoons":
              return Unit.TSP;
            case "tablespoon":
            case "tablespoons":
              return Unit.TBSP;
            case "cup":
            case "cups":
              return Unit.C;
            case "pint":
            case "pints":
              return Unit.PT;
            case "quart":
            case "quarts":
              return Unit.QT;
            case "gallon":
            case "gallons":
              return Unit.GAL;
            case "ounce":
            case "ounces":
            case "fluid ounce":
            case "fluid ounces":
              return Unit.OZ;
            case "pound":
            case "pounds":
              return Unit.LB;
            default:
              return Unit.OTHER;
          }
        }
      }
    }
  }

  @NonNull
  @Override
  public String toString() {
    return (getQuantity() + " " + ((getUnit() != Unit.OTHER) ? getUnit().toString() : getUnitAlt())
        + " " + getName());
  }

  @Ignore
  private int hashCode;

  @Override
  public int hashCode() {
    if (hashCode == 0) {
      hashCode = Objects.hash(quantity, unit, name);
    }
    return hashCode;
  }

  @Override
  public boolean equals(Object obj) {
    boolean result = false;
    if (obj == this) {
      result = true;
    } else if (obj instanceof Ingredient && obj.hashCode() == hashCode()) {
      Ingredient other = (Ingredient) obj;
      result = quantity.equals(other.quantity)
          && name.equals(other.name)
          && unit.equals(other.unit)
          && unitAlt == null ? other.unitAlt == null : (unitAlt.equalsIgnoreCase(other.unitAlt));
    }
    return result;
  }

}
