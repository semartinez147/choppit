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
 TODO: Add Recipe foreign key to Ingredient
 TODO: Remove Step foreign key from Ingredient
 TODO: Add List<Ingredient> to Recipe
 TODO: Remove List<Ingredient> from Step
 TODO: add Ingredient logic to RecipePojo & delete StepPojo

 TODO: Update IngredientRecyclerAdapter to get List<Ingredient> from Recipe
 TODO: Update RecipeRecyclerAdapter to get List<Ingredient> from Recipe
 TODO: Update EditingRecyclerAdapter to get List<Ingredient> from Recipe
 TODO: Update EditingFragment.emptyRecipe() for new hierarchy

 TODO: Update RecipeRepository.save() to assign RecipeIds instead of StepIds to Ingredients
 TODO: Update JsoupRetriever.process() and/or rewrite most of the retriever logic
 TODO: Update MVM to match new retriever logic
 TODO: Make sure LoadingFragment process matches MVM logic

 TODO: Update Recipe.populateData() and Step/Ingredient methods
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
