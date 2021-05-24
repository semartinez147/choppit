package com.semartinez.projects.choppit.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

/**
 * Steps are child entities of {@link Recipe}s.  Each Step consists of an instruction String, and recipe order integer.
 */
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
  @ColumnInfo(name = "recipe_order")
  private int recipeOrder;

  public Step() {
  }

  /**
   * @param recipeId is the id of the parent {@link Recipe}.
   * @param instructions is the instruction text.
   * @param recipeOrder is the order to display this Step in its Recipe.
   */
  @Ignore
  public Step(long recipeId, @NotNull String instructions, int recipeOrder) {
    super();
    this.recipeId = recipeId;
    this.instructions = instructions;
    this.recipeOrder = recipeOrder;
  }

  /**
   * @return the id of the Step
   */
  public long getStepId() {
    return stepId;
  }

  /**
   * @param stepId is generated when a Step is inserted into the database.
   */
  public void setStepId(long stepId) {
    this.stepId = stepId;
  }

  /**
   * @return returns the recipeId of the parent Recipe.
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
   * @return the instruction String for a step.
   */
  public @NotNull String getInstructions() {
    return instructions;
  }

  /**
   * @param instructions is set by the user in the EditingFragment.
   */
  public void setInstructions(@NotNull String instructions) {
    this.instructions = instructions;
  }

  /**
   * @return the order to display this Step in its Recipe.
   */
  public int getRecipeOrder() {
    return recipeOrder;
  }

  /**
   * @param recipeOrder determined by the Step's position in the EditingFragment.
   */
  public void setRecipeOrder(int recipeOrder) {
    this.recipeOrder = recipeOrder;
  }

  /**
   * @return the step number and first 20 characters of the Step.
   */
  @NonNull
  @Override
  public String toString() {
    return ("Step " + getRecipeOrder() + ": " + getInstructions().substring(0, 20) + " ...");
  }
}
