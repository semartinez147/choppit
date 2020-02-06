package edu.cnm.deepdive.choppit.model.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    indices = {
        @Index(value = "instructions", unique = true)
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

  @ColumnInfo(name = "recipe_id")
  private long recipeId;

  @ColumnInfo
  private String instructions;

  @ColumnInfo (name = "recipe_order", index = true)
  private int recipeOrder;

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
}
