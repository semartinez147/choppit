package edu.cnm.deepdive.choppit.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface RecipeDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Recipe recipe);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Recipe> recipes);

  @Update
  Single<Integer> update(Recipe recipe);

  @Delete
  Single<Integer> delete(Recipe... recipes);

  @Query("SELECT * FROM Recipe ORDER BY title")
  List<Recipe> list();

  @Query("SELECT * FROM Recipe WHERE Favorite = 1 ORDER BY title")
  List<Recipe> favList();

  @Query("SELECT * FROM Recipe WHERE Edited = 1 ORDER BY title")
  List<Recipe> editedList();

  @Query("SELECT * FROM Recipe WHERE title = :title")
  Single<Recipe> select(String title);
}
