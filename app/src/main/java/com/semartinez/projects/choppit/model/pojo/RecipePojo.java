package com.semartinez.projects.choppit.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.List;

/**
 * RecipePojo exists to facilitate database queries.  {@link com.semartinez.projects.choppit.model.dao.RecipeDao}
 * methods return a RecipePojo with all nested objects, which is then converted back to a Recipe
 * before being sent to the frontend.
 */
public class RecipePojo {

  public RecipePojo() {
  }

  @Embedded
  private Recipe recipe;

  @Relation(parentColumn = "recipe_id", entityColumn = "recipe_id", entity = Step.class)
  private List<Step> steps;

  @Relation(parentColumn = "recipe_id", entityColumn = "recipe_id", entity = Ingredient.class)
  private List<Ingredient> ingredients;

  /**
   * @return the embedded Recipe.
   */
  public Recipe getRecipe() {
    return recipe;
  }

  /**
   * @param recipe an entry retrieved from the database.
   */
  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  /**
   * @return the Steps nested in the Recipe.
   */
  public List<Step> getSteps() {
    return steps;
  }

  /**
   * @param steps entries retrieved from the database.
   */
  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  /**
   * @return the Ingredients nested in the Recie
   */
  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  /**
   * @param ingredients entries retrieved from the database.
   */
  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }
}
