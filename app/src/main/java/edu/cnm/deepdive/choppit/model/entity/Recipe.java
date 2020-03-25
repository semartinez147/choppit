package edu.cnm.deepdive.choppit.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import edu.cnm.deepdive.choppit.model.dao.StepDao;
import edu.cnm.deepdive.choppit.model.entity.Ingredient.Unit;
import edu.cnm.deepdive.choppit.model.pojo.RecipeWithDetails;
import edu.cnm.deepdive.choppit.model.pojo.StepWithDetails;
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

  @ColumnInfo(name = "edited", index = true, defaultValue = "false")
  private boolean edited;

  @ColumnInfo(name = "favorite", index = true, defaultValue = "false")
  private boolean favorite;

  @Ignore
  private List<Step> steps = null;

  public Recipe() {

  }

  @Ignore
  public Recipe(String url, String title, boolean edited, boolean favorite, List<Step> steps) {
    super();
    this.url = url;
    this.title = title;
    this.edited = edited;
    this.favorite = favorite;
    this.steps = steps;
  }

  /* Stretch goal fields (nullable):
  private String meal;
  private List<String> tag;
  private String image;
  */

  public Recipe(RecipeWithDetails recipeWithDetails) {
    this.id = recipeWithDetails.getRecipe().getId();
    this.url = recipeWithDetails.getRecipe().getUrl();
    this.title = recipeWithDetails.getRecipe().getTitle();
    this.edited = recipeWithDetails.getRecipe().isEdited();
    this.favorite = recipeWithDetails.getRecipe().isFavorite();
    this.steps = this.getSteps(recipeWithDetails.getStepWithDetails());
  }

  private List<Step> getSteps(List<StepWithDetails> stepWithDetails) {
    for (StepWithDetails details : stepWithDetails) {
      Step step = details.getStep();
      step.setIngredients(details.getIngredients());
      this.addStep(details.getStep()); // wrote pseudo-setter
    }
    return this.steps;
  }

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
    this.favorite = favorite;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  public void addStep(Step step) { // pseudo-setter
    this.steps.add(step);
  } // pseudo-setter for getSteps method


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


  public static List<Step> fakeSteps() {
    List<Step> fakeSteps = new ArrayList<>();
    List<Ingredient> fakeIngredients = new ArrayList<>();
    fakeIngredients.addAll(Arrays.asList(phonyIngredients));
    for (int j = 0, i = 0; i < 5; i++, j++) {
      Step step = new Step();
      step.setRecipeOrder(i);
      step.addIngredient(fakeIngredients.get(j));
      step.setInstructions("Follow instructions " + (5-i) + " more times");
      fakeSteps.add(step);
    }
    return fakeSteps;
  }

  public static Recipe[] populateData() {
    return new Recipe[]{
        new Recipe("www.cookies.com", "Cookies", false, false, fakeSteps()),
        new Recipe("www.biscuits.com", "Biscuits", false, true, fakeSteps()),
        new Recipe("www.grilledcheese.com", "Grilled Cheese", false, false, fakeSteps()),
        new Recipe("www.spanakopita.com", "Spanakopita", true, false, fakeSteps()),
        new Recipe("www.lutherburger.com", "Luther Burger", false, false, fakeSteps()),
        new Recipe("www.chronwich.com", "The Chronwich", true, true, fakeSteps()),
    };
  }
}
