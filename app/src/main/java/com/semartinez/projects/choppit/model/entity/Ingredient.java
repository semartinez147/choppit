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

/**
 * Ingredients are child entities of {@link Recipe}s.  Each Ingredient consists of a {@link
 * #quantity} String, a {@link #unit} {@link Unit}, and a {@link #name} String.  Unit is primarily
 * an {@link Enum} of abbreviated cooking terms, but also allows the value {@link Unit#OTHER} with
 * {@link #unitAlt} as a String stand-in for less-common terms (i.e. sprig) and items measured whole
 * (i.e. eggs)
 */
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

  public Ingredient() {
  }

  /**
   * @param recipeId received from the parent {@link Recipe}
   * @param quantity is the amount of {@link Unit}.  Takes a String to avoid dealing with
   *                 fraction/decimal conversions and unicode characters like '⅔'.
   * @param unit     is the unit of measurement.
   * @param unitAlt  is optional; used if the unit parameter is {@link Unit#OTHER}.
   * @param name     is the name of the ingredient.
   */
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

  /**
   * @return the id of the Ingredient.
   */
  public long getId() {
    return id;
  }

  /**
   * @param id is generated when an Ingredient is inserted into the database.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the recipeId of the parent Recipe
   */
  public long getRecipeId() {
    return recipeId;
  }

  /**
   * @param recipeId is received from the parent Recipe.
   */
  public void setRecipeId(long recipeId) {
    this.recipeId = recipeId;
  }

  /**
   * @return the Ingredient's quantity
   */
  public String getQuantity() {
    return quantity;
  }

  /**
   * @param quantity is set by the user in the EditingFragment.
   */
  public void setQuantity(String quantity) {
    // TODO: Remove anything that isn't a number, space or "/"

    this.quantity = quantity;
  }

  /**
   * @return the Ingredient's Unit.
   */
  public Unit getUnit() {
    return unit;
  }

  /**
   * @param unit is set by the user in the EditingFragment.
   */
  public void setUnit(Unit unit) {
    this.unit = unit;
    unitAlt = this.unit.toString();
  }

  /**
   * @return the String value of the Ingredient's Unit.
   */
  public String getUnitText() {
    return unitAlt.equalsIgnoreCase("other") ? " " : unitAlt;
  }

  /**
   * @param unit is set by the user in the EditingFragment.
   */
  public void setUnitText(String unit) {
    unitAlt = unit;
    this.unit = Unit.toUnit(unit != null ? unit : "other");
  }

  /**
   * @return a String if the Ingredient's Unit is OTHER.
   */
  public String getUnitAlt() {
    return unitAlt;
  }

  /**
   * @param unitAlt is set by the user in the EditingFragment.
   */
  public void setUnitAlt(String unitAlt) {
    this.unitAlt = unitAlt;
  }

  /**
   * @return the name of the Ingredient.
   */
  public String getName() {
    return name;
  }

  /**
   * @param name is set by the user in the EditingFragment.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * This Enum holds values for units of measurement with an "OTHER" option to handle additional
   * values.
   */
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


    /**
     * @param value is a Unit Enum
     * @return the String value of the Unit
     */
    @TypeConverter
    public static String toString(Unit value) {
      return (value != null) ? value.name() : null;

    }

    /**
     * This converter essentially replaces common spelling with abbreviated measurement names (i.e.
     * "teaspoon" and "teaspoons" both become "TSP."
     *
     * @param value is a String Unit
     * @return a Unit value (OTHER if the param is not a standard unit).
     */
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

  /**
   * @return Quantity + Unit + Name.
   */
  @NonNull
  @Override
  public String toString() {
    return (getQuantity() + " " + ((getUnit() != Unit.OTHER) ? getUnit().toString() : getUnitAlt())
        + " " + getName());
  }

  @Ignore
  private int hashCode;

  /**
   * @return a hash based on the contents of the Ingredient.
   */
  @Override
  public int hashCode() {
    if (hashCode == 0) {
      hashCode = Objects.hash(quantity, unit, name);
    }
    return hashCode;
  }

  /**
   * @param obj another Ingredient to compare
   * @return true if the quantity, unit, unitAlt and name are all equal.
   */
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
