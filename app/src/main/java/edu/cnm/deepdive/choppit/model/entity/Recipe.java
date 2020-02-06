package edu.cnm.deepdive.choppit.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    indices = {
        @Index(value = {"title", "url"}, unique = true)
    }
)
public class Recipe {

  @ColumnInfo(name = "recipe_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo (name = "url", defaultValue = "null", collate = ColumnInfo.NOCASE) // does this default work?
    private String url;

  @ColumnInfo(name = "title", index = true, defaultValue = "Untitled Recipe", collate = ColumnInfo.NOCASE)
  private String title;

  @ColumnInfo(name = "edited", index = true, defaultValue = "false")
  private boolean edited;

  @ColumnInfo(name = "favorite", index = true, defaultValue = "false")
  private boolean favorite;

  /* Stretch goal fields (nullable):
  private String meal;
  private List<String> tag;
  private String image;
  */

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isEdited() {
    return edited;
  }

  public void setEdited(boolean edited) {
    this.edited = edited;
  }

  public boolean isFavorite() {
    return favorite;
  }

  public void setFavorite(boolean favorite) {
    this.favorite = favorite; }
}
