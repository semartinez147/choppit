package edu.cnm.deepdive.choppit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.choppit.model.entity.Item;
import edu.cnm.deepdive.choppit.model.entity.Step;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface StepDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert (Step step);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert (Collection<Step> steps);

  @Update
  Single<Integer> update(Step step);

  @Delete
  Single<Integer> delete(Step step);

  @Query("SELECT * FROM Step ORDER BY recipe_order")
  LiveData<List<Step>> select();
}
