package com.semartinez.projects.choppit.model.entity;

import com.semartinez.projects.choppit.model.pojo.RecipePojo;
import junit.framework.TestCase;
import org.junit.Test;

public class RecipeTest extends TestCase {

  private static final Recipe[] recipes = Recipe.populateData();
  private static final RecipePojo recipePojo = new RecipePojo();

  @Test
  void pojoIngredients() {
    for (Recipe recipe : recipes) {
      recipePojo.setRecipe(recipe);
      assertEquals(recipe.getIngredients(), recipePojo.getIngredients());
    }
  }

}