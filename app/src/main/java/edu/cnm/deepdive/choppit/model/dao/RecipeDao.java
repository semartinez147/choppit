package edu.cnm.deepdive.choppit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface RecipeDao {

  String RECIPE_DETAILS_QUERY = "SELECT r.* FROM Recipe AS r"; // TODO write the rest of the query.

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Recipe recipe);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Recipe> recipes);

  @Update
  Single<Integer> update(Recipe recipe);

  @Delete
  Single<Integer> delete(Recipe... recipes);

  @Query("SELECT * FROM Recipe ORDER BY title DESC")
  LiveData<List<Recipe>> select();

  @Query("SELECT * FROM Recipe WHERE favorite ORDER BY title")
  LiveData<List<Recipe>> favList();

  @Query("SELECT * FROM Recipe WHERE title = :title")
  Maybe<Recipe> select(String title);

  @Query("SELECT * FROM Recipe WHERE recipe_id = :id")
  Maybe<Recipe> getOne(long id);

  @Insert
  void populate(Recipe... recipes);
}
