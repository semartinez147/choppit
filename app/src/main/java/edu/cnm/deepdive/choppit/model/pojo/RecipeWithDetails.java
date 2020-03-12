package edu.cnm.deepdive.choppit.model.pojo;

import androidx.room.Embedded;
import edu.cnm.deepdive.choppit.model.entity.Recipe;
import javax.annotation.Nonnull;

public class RecipeWithDetails {

  @Nonnull
  @Embedded
  private Recipe recipe;

  private String url;
  private String title;

  private boolean favorite;
  private boolean edited;

  @Nonnull
  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(@Nonnull Recipe recipe) {
    this.recipe = recipe;
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

  public boolean isFavorite() {
    return favorite;
  }

  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }

  public boolean isEdited() {
    return edited;
  }

  public void setEdited(boolean edited) {
    this.edited = edited;
  }
}
