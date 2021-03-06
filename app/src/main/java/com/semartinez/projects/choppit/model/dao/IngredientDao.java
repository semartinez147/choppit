package com.semartinez.projects.choppit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface IngredientDao {

  @Insert
  Single<Long> insert(Ingredient ingredient);

  @Insert
  List<Long> insert(Collection<Ingredient> ingredients);

  @Update
  int update(Ingredient ingredient);

  @Delete
  int delete(Ingredient... ingredients);

  @Query("SELECT * FROM Ingredient")
  LiveData<List<Ingredient>> list();

  @Query("SELECT * FROM Ingredient WHERE recipe_id = :id")
  Single<Ingredient> select(long id);
}
