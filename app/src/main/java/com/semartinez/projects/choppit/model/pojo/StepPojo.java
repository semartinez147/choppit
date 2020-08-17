package com.semartinez.projects.choppit.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.List;

public class StepPojo {

  public StepPojo() {
  }

  @Embedded
  private Step step;

  @Relation(parentColumn = "step_id", entityColumn = "step_id", entity = Ingredient.class)
  private List<Ingredient> ingredientList;

  public Step getStep() {
    return step;
  }

  public void setStep(Step step) {
    this.step = step;
  }

  public List<Ingredient> getIngredientList() {
    return ingredientList;
  }

  public void setIngredientList(
      List<Ingredient> ingredientList) {
    this.ingredientList = ingredientList;
  }
}
