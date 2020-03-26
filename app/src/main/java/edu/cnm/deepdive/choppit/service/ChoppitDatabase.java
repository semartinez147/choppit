package edu.cnm.deepdive.choppit.service;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.choppit.model.dao.RecipeDao;
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

    /* TODO disable for production*/
    private static final ChoppitDatabase INSTANCE = dummyDatabase(context);


    /* TODO enable for production */
//    private static final ChoppitDatabase INSTANCE = Room.databaseBuilder(
//        context, ChoppitDatabase.class, DB_NAME).build();


  }


  public abstract RecipeDao getRecipeDao();

  private static ChoppitDatabase dummyDatabase(final Context context) {
    return Room.databaseBuilder(context, ChoppitDatabase.class, "choppit_db")
        .addCallback(new Callback() {
          @Override
          public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadScheduledExecutor().execute(
                () -> getInstance().getRecipeDao().populate(Recipe.populateData()));
          }
        })
        .build();
  }


}
