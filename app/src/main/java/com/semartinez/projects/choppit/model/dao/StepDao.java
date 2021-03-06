package com.semartinez.projects.choppit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.semartinez.projects.choppit.model.entity.Step;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface StepDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  Single<Long> insert (Step step);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  Single<List<Long>> insert (Collection<Step> steps);

  @Update
  Single<Integer> update(Step step);

  @Delete
  Single<Integer> delete(Step step);

  @Query("SELECT * FROM Step WHERE recipe_id = :recipe_id ORDER BY recipe_order")
  LiveData<List<Step>> list(long recipe_id);

  @Query("SELECT * FROM Step WHERE step_id = :step_id")
  Single<Step> select(long step_id);
}
