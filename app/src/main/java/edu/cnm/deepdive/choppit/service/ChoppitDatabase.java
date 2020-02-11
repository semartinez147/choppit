package edu.cnm.deepdive.choppit.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import edu.cnm.deepdive.choppit.model.dao.IngredientDao;
import edu.cnm.deepdive.choppit.model.dao.ItemDao;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
import edu.cnm.deepdive.choppit.model.dao.StepDao;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Item;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;

@Database(
    entities = {Ingredient.class, Item.class, Recipe.class, Step.class},
    version = 1,
    exportSchema = true
)
// TODO TypeConverters annotation
public abstract class ChoppitDatabase extends RoomDatabase {

  private static final String DB_NAME = "choppit_db";

  private static Application context;

  public static void setContext(Application context) {
    ChoppitDatabase.context = context;
  }


  // TODO get getters to get something
  public static ChoppitDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public abstract IngredientDao getIngredientDao();

  public abstract ItemDao getItemDao();

  public abstract RecipeDao getRecipeDao();

  public abstract StepDao getStepDao();

  private static class InstanceHolder {

    private static final ChoppitDatabase INSTANCE = Room.databaseBuilder(
        context, ChoppitDatabase.class, DB_NAME)
        .build();
  }

  public static class Converters {
    // TODO write converters
  }
}
