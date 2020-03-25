package edu.cnm.deepdive.choppit.service;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.choppit.model.dao.IngredientDao;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
import edu.cnm.deepdive.choppit.model.dao.StepDao;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Ingredient.Unit;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.service.ChoppitDatabase.Converters;
import java.util.Date;
import java.util.concurrent.Executors;

@Database(
    entities = {Ingredient.class, Recipe.class, Step.class},
    version = 1,
    exportSchema = true
)
@TypeConverters({Converters.class, Unit.class})
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

    /* FIXME for debugging*/
    private static final ChoppitDatabase INSTANCE = dummyDatabase(context);
    /* FIXME for debugging*/


    /* FIXME use this return for a real database */
//    private static final ChoppitDatabase INSTANCE = Room.databaseBuilder(
//        context, ChoppitDatabase.class, DB_NAME).build();
    /* FIXME use this return for a real database */

  }


  public abstract IngredientDao getIngredientDao();

  public abstract RecipeDao getRecipeDao();

  public abstract StepDao getStepDao();

  private static ChoppitDatabase dummyDatabase(final Context context) {
    return Room.databaseBuilder(context, ChoppitDatabase.class, "choppit_db")
        .addCallback(new Callback() {
          @Override
          public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
              @Override
              public void run() {
                getInstance().getRecipeDao().populate(Recipe.populateData());
              }
            });
          }
        })
        .build();
  }

  public static class Converters {

    @TypeConverter
    public static Long fromDate(Date date) {
      return (date != null) ? date.getTime() : null;
    }

    @TypeConverter
    public static Date fromLong(Long value) {
      return (value != null) ? new Date(value) : null;
    }

  }

}
