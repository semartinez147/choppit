package edu.cnm.deepdive.choppit.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Step;
import java.util.List;
import javax.annotation.Nonnull;

public class StepWithDetails {

  @Nonnull
  @Embedded
  private Step step;

  @Relation(parentColumn = "id", entityColumn = "stepId", entity = Ingredient.class)
  private List<Ingredient> ingredients;

  @Nonnull
  public Step getStep() {
    return step;
  }

  public void setStep(@Nonnull Step step) {
    this.step = step;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }
}
