package edu.cnm.deepdive.choppit.model.pojo;

import androidx.room.Embedded;
import edu.cnm.deepdive.choppit.model.entity.Step;
import javax.annotation.Nonnull;

public class StepWithDetails {

  @Nonnull
  @Embedded
  private Step step;

  private long recipeId;
  private int recipeOrder;

  private String instructions;

  @Nonnull
  public Step getStep() {
    return step;
  }

  public void setStep(@Nonnull Step step) {
    this.step = step;
  }

  public long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(long recipeId) {
    this.recipeId = recipeId;
  }

  public int getRecipeOrder() {
    return recipeOrder;
  }

  public void setRecipeOrder(int recipeOrder) {
    this.recipeOrder = recipeOrder;
  }

  public String getInstructions() {
    return instructions;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }
}
