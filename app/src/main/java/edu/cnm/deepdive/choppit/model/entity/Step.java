package edu.cnm.deepdive.choppit.model.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.util.List;

@Entity(
    indices = {
        @Index(value = {"recipe_id", "recipe_order"}, unique = true)
    },
    foreignKeys = {
        @ForeignKey(
            entity = Recipe.class,
            parentColumns = "recipe_id",
            childColumns = "recipe_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Step {

  @ColumnInfo(name = "step_id")
  @PrimaryKey(autoGenerate = true)
  private long stepId;

  @NonNull
  @ColumnInfo(name = "recipe_id", index = true)
  private long recipeId;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String instructions;

  @NonNull
  @ColumnInfo (name = "recipe_order")
  private int recipeOrder;

  @Ignore
  private List<Ingredient> ingredients = null;

  public Step() {

  }

  @Ignore
  public Step (long recipeId, String instructions, int recipeOrder, List<Ingredient> ingredients) {
    super();
    this.recipeId = recipeId;
    this.instructions = instructions;
    this.recipeOrder = recipeOrder;
    this.ingredients = ingredients;
  }

  public long getStepId() {
    return stepId;
  }

  public void setStepId(long stepId) {
    this.stepId = stepId;
  }

  public long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(long recipeId) {
    this.recipeId = recipeId;
  }

  public String getInstructions() {
    return instructions;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  public int getRecipeOrder() {
    return recipeOrder;
  }

  public void setRecipeOrder(int recipeOrder) {
    this.recipeOrder = recipeOrder;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }
}
