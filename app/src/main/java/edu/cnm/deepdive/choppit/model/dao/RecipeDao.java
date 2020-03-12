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
import edu.cnm.deepdive.choppit.model.pojo.RecipeWithDetails;
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


  // working from https://medium.com/swlh/android-room-persistence-library-relations-in-a-nested-one-to-many-relationship-f2fe21c9e1ad
  @Transaction
  @Query("SELECT * FROM Recipe")
  LiveData<List<RecipeWithDetails>> loadRecipes();

  @Query(RECIPE_DETAILS_QUERY)
  LiveData<List<RecipeWithDetails>> selectWithDetails();

  @Query("SELECT * FROM Recipe WHERE favorite ORDER BY title")
  LiveData<List<Recipe>> favList();

  @Query("SELECT * FROM Recipe WHERE edited ORDER BY title")
  LiveData<List<Recipe>> editedList();

  @Query("SELECT * FROM Recipe WHERE title = :title")
  Maybe<Recipe> select(String title);

  @Query("SELECT * FROM Recipe WHERE recipe_id = :id")
  Maybe<Recipe> getOne(long id);

  @Query("SELECT * FROM Recipe WHERE recipe_id = :id")
  Single<RecipeWithDetails> selectOne(long id);
}
