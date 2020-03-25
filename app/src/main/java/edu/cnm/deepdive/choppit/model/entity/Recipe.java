package edu.cnm.deepdive.choppit.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import edu.cnm.deepdive.choppit.model.entity.Ingredient.Unit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(
    indices = {
        @Index(value = {"title", "url"}, unique = true),
    }
)

public class Recipe {

  @ColumnInfo(name = "recipe_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "url", collate = ColumnInfo.NOCASE)
  private String url;

  @NonNull
  @ColumnInfo(name = "title", index = true, defaultValue = "Untitled Recipe", collate = ColumnInfo.NOCASE)
  private String title;

  @ColumnInfo(name = "favorite", index = true, defaultValue = "false")
  private boolean favorite;

  @Ignore
  private List<Step> steps = null;

  /**
   * Recipe is the top-level {@link Entity} in the database.  It gets a non-editable {@link #url} if
   * it was loaded from a website.  {@link #title}, {@link #favorite} and the list of {@link Step}s
   * can be customized by the user.
   */
  public Recipe() {
  }

  @Ignore
  public Recipe(String url, String title, boolean favorite, List<Step> steps) {
    super();
    this.url = url;
    this.title = title;
    this.favorite = favorite;
    this.steps = steps;
  }

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

  public boolean isFavorite() {
    return favorite;
  }

  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  @NonNull
  @Override
  public String toString() {
    return getTitle();
  }

  // TODO delete everything below here for production.
  /* PAY NO ATTENTION TO THE DATABASE METHODS BELOW */

  private static Ingredient[] phonyIngredients = {
      new Ingredient(0, "3", Unit.OZ, null, "cardamom"),
      new Ingredient(0, "2 1/2", Unit.DASH, null, "uranium"),
      new Ingredient(0, "17", Unit.OTHER, "whole", "marijuanas"),
      new Ingredient(0, "2 1/3", Unit.PT, null, "pureed asparagus"),
      new Ingredient(0, "4 1/4", Unit.OTHER, "sprigs", "Douglas Fir"),
      new Ingredient(0, "1", Unit.GAL, null, "Friday"),
      new Ingredient(0, "3", Unit.TBSP, null, "salted butter"),
      new Ingredient(0, "1", Unit.QT, null, "pie"),
      new Ingredient(0, "3", Unit.OTHER, "legs of", "lamb"),
      new Ingredient(0, "1 1/8", Unit.LB, null, "octothorpes"),
  };


  private static List<Step> fakeSteps() {
    List<Step> fakeSteps = new ArrayList<>();
    List<Ingredient> fakeIngredients = new ArrayList<>();
    fakeIngredients.addAll(Arrays.asList(phonyIngredients));
    for (int j = 0, i = 0; i < 5; i++, j++) {
      Step step = new Step();
      step.setRecipeOrder(i);
      step.addIngredient(fakeIngredients.get(j));
      step.setInstructions("Follow instructions " + (5 - i) + " more times");
      fakeSteps.add(step);
    }
    return fakeSteps;
  }

  /**
   * This array populates the database with dummy recipes.
   *
   * @return a list of bogus recipe objects.
   */
  public static Recipe[] populateData() {
    return new Recipe[]{
        new Recipe("www.cookies.com", "Cookies", false, fakeSteps()),
        new Recipe("www.biscuits.com", "Biscuits", true, fakeSteps()),
        new Recipe("www.grilledcheese.com", "Grilled Cheese", false, fakeSteps()),
        new Recipe("www.spanakopita.com", "Spanakopita", false, fakeSteps()),
        new Recipe("www.lutherburger.com", "Luther Burger", false, fakeSteps()),
        new Recipe("www.chronwich.com", "The Chronwich", true, fakeSteps()),
    };
  }
}
