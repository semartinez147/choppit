package edu.cnm.deepdive.choppit.service;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.choppit.model.dao.IngredientDao;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
import edu.cnm.deepdive.choppit.model.dao.StepDao;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Ingredient.Unit;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import java.util.concurrent.Executors;

@Database(
    entities = {Ingredient.class, Recipe.class, Step.class},
    version = 1,
    exportSchema = true
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
