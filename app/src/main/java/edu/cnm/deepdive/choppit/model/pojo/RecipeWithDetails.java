package edu.cnm.deepdive.choppit.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import java.util.List;
import javax.annotation.Nonnull;

public class RecipeWithDetails {

  public RecipeWithDetails() {

  }

  @Nonnull
  @Embedded
  private Recipe recipe;

  @Relation(parentColumn = "id", entityColumn = "recipeId", entity = Step.class)
  private List<StepWithDetails> stepWithDetails;

  @Nonnull
  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(@Nonnull Recipe recipe) {
    this.recipe = recipe;
  }

  public List<StepWithDetails> getStepWithDetails() {
    return stepWithDetails;
  }

  public void setStepWithDetails(
      List<StepWithDetails> stepWithDetails) {
    this.stepWithDetails = stepWithDetails;
  }
}
