package edu.cnm.deepdive.choppit.model.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(
    indices = {
        @Index(value = "item_name", unique = true)
    }
)
public class Item {

  @ColumnInfo(name = "item_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  @ColumnInfo(name = "item_name", collate = ColumnInfo.NOCASE)
  private String name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
