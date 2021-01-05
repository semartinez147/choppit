package com.semartinez.projects.choppit.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.semartinez.projects.choppit.model.entity.Recipe.RecipeComponent;
import java.util.List;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"NullableProblems", "NotNullFieldNotInitialized"})
@Entity(
    indices = {
        @Index(value = {"recipe_id", "recipe_order"}, unique = true)
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
public class Step implements RecipeComponent {

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
  @ColumnInfo(name = "recipe_order")
  private int recipeOrder;

  /**
   * Each {@link Recipe} contains a {@link List} of Steps.  {@link #recipeOrder} is set automatically during
   * editing; {@link #instructions} are user-editable.
   */
  public Step() {
  }

  @Ignore
  public Step(long recipeId, @NotNull String instructions, int recipeOrder) {
    super();
    this.recipeId = recipeId;
    this.instructions = instructions;
    this.recipeOrder = recipeOrder;
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

  public @NotNull String getInstructions() {
    return instructions;
  }

  public void setInstructions(@NotNull String instructions) {
    this.instructions = instructions;
  }

  public int getRecipeOrder() {
    return recipeOrder;
  }

  public void setRecipeOrder(int recipeOrder) {
    this.recipeOrder = recipeOrder;
  }

  @NonNull
  @Override
  public String toString() {
    return ("Step " + getRecipeOrder() + ": " + getInstructions().substring(0, 20) + " ...");
  }
}
