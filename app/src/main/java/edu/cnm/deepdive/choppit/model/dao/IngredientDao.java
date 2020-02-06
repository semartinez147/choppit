package edu.cnm.deepdive.choppit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import java.util.Collection;
import java.util.List;

@Dao
public interface IngredientDao {

  @Insert
  long insert(Ingredient ingredient);

  @Insert
  List<Long> insert(Collection<Ingredient> ingredients);

  @Update
  int update(Ingredient ingredient);

  @Delete
  int delete(Ingredient... ingredients);

  @Query("SELECT * FROM Ingredient ORDER BY item_name")
  LiveData<List<Ingredient>> select();
}
