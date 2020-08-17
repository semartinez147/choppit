package com.semartinez.projects.choppit.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.List;

public class RecipePojo {

  public RecipePojo() {
  }

  @Embedded
  private Recipe recipe;

  @Relation(parentColumn = "recipe_id", entityColumn = "recipe_id", entity = Step.class)
  private List<StepPojo> steps;

  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public List<StepPojo> getSteps() {
    return steps;
  }

  public void setSteps(List<StepPojo> steps) {
    this.steps = steps;
  }
}
