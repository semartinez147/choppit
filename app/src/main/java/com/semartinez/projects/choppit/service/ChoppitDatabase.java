package com.semartinez.projects.choppit.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.semartinez.projects.choppit.model.dao.IngredientDao;
import com.semartinez.projects.choppit.model.dao.RecipeDao;
import com.semartinez.projects.choppit.model.dao.StepDao;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Ingredient.Unit;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.entity.Step;

/* Hierarchy restructuring task list:
 DONE: Add Recipe foreign key to Ingredient
 DONE: Remove Step foreign key from Ingredient
 DONE: Add List<Ingredient> to Recipe
 DONE: Remove List<Ingredient> from Step
 DONE: Add Ingredient logic to RecipePojo & delete StepPojo

 DONE: Update IngredientRecyclerAdapter to get List<Ingredient> from Recipe
 DONE: Update RecipeRecyclerAdapter to get List<Ingredient> from Recipe
 DONE: Update EditingRecyclerAdapter to get List<Ingredient> from Recipe
 DONE: Update EditingFragment.emptyRecipe() for new hierarchy

 DONE: Update RecipeRepository.save() to assign RecipeIds instead of StepIds to Ingredients
 DONE: Update JsoupRetriever.process() and/or rewrite most of the retriever logic
 DONE: Update MVM to match new retriever logic
 DONE: Make sure LoadingFragment process matches MVM logic

 DONE: Update Recipe.populateData() and Step/Ingredient methods
 TODO: Test results
 TODO: Update ERD
  */

@Database(
    entities = {Ingredient.class, Recipe.class, Step.class},
    version = 1
)
@TypeConverters({Unit.class})
public abstract class ChoppitDatabase extends RoomDatabase {

  private static final String DB_NAME = "choppit_db";

  private static Application context;

  public static void setContext(Application context) {
    ChoppitDatabase.context = context;
  }


  public static ChoppitDatabase getInstance() {

    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {


    private static final ChoppitDatabase INSTANCE = Room.databaseBuilder(
        context, ChoppitDatabase.class, DB_NAME).build();
  }


  public abstract RecipeDao getRecipeDao();

  public abstract StepDao getStepDao();

  public abstract IngredientDao getIngredientDao();
}
