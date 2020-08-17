package com.semartinez.projects.choppit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.semartinez.projects.choppit.model.entity.Recipe;
import com.semartinez.projects.choppit.model.pojo.RecipePojo;
import io.reactivex.Maybe;
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

  @Query("SELECT * FROM Recipe ORDER BY title DESC")
  LiveData<List<Recipe>> select();

  @Query("SELECT * FROM Recipe WHERE favorite ORDER BY title")
  LiveData<List<Recipe>> favList();

  @Query("SELECT * FROM Recipe WHERE title = :title")
  Maybe<Recipe> select(String title);

  @Query("SELECT * FROM Recipe WHERE recipe_id = :id")
  Single<Recipe> getOne(long id);

  @Query("SELECT * FROM Recipe LIMIT 1")
  Single<Recipe> check();

  @Transaction
  @Query("SELECT * FROM Recipe")
  LiveData<RecipePojo> loadRecipeData();
}
