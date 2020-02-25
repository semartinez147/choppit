package edu.cnm.deepdive.choppit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.choppit.model.entity.Item;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface ItemDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Item item);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Item> items);

  @Update
  Single<Integer> update(Item item);

  @Delete
  Single<Integer> delete(Item item);

  @Query("SELECT * FROM Item ORDER BY item_name")
  List<Item> select();

  @Query("SELECT * FROM Item WHERE item_id = :id")
  Single<Item> select(long id);
}
