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

/**
 * This is my Room Database.  There are many like it, but this one is mine.
 */
@Database(
    entities = {Ingredient.class, Recipe.class, Step.class},
    version = 1
)
@TypeConverters({Unit.class})
public abstract class ChoppitDatabase extends RoomDatabase {

  private static final String DB_NAME = "choppit_db";

  private static Application context;

   /**
   * @param context ChoppitApplication.
   */
  public static void setContext(Application context) {
    ChoppitDatabase.context = context;
  }


  /**
   * @return a singleton instance of this class.
   */
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
