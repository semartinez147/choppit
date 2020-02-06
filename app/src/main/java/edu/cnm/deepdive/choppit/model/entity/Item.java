package edu.cnm.deepdive.choppit.model.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    indices = {
        @Index(value = "item_name", unique = true)
    }
)
public class Item {

  @ColumnInfo(name = "item_id")
  @PrimaryKey(autoGenerate = true)
  private long itemId;

  @ColumnInfo(name = "item_name", collate = ColumnInfo.NOCASE)
  private String itemName;

  public long getItemId() {
    return itemId;
  }

  public void setItemId(long itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }
}
