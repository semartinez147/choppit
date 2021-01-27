package com.semartinez.projects.choppit.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.List;

public class RecipePojo {

  public RecipePojo() {
  }

  @Embedded
  private Recipe recipe;

  @Relation(parentColumn = "recipe_id", entityColumn = "recipe_id", entity = Step.class)
  private List<Step> steps;

  @Relation(parentColumn = "recipe_id", entityColumn = "recipe_id", entity = Ingredient.class)
  private List<Ingredient> ingredients;

  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }
}
