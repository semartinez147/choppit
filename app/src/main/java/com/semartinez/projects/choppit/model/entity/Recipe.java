package com.semartinez.projects.choppit.model.entity;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.semartinez.projects.choppit.BR;
import com.semartinez.projects.choppit.model.entity.Ingredient.Unit;
import com.semartinez.projects.choppit.model.pojo.RecipePojo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NotNullFieldNotInitialized")
@Entity(
    indices = {
        @Index(value = {"title", "url"}, unique = true),
    }
)

public class Recipe extends BaseObservable {

  @ColumnInfo(name = "recipe_id")
  @PrimaryKey(autoGenerate = true)
  private long recipeId;

  @ColumnInfo(name = "url", collate = ColumnInfo.NOCASE)
  private String url;

  @NonNull
  @ColumnInfo(name = "title", index = true, defaultValue = "Untitled Recipe", collate = ColumnInfo.NOCASE)
  private String title;

  @ColumnInfo(name = "favorite", index = true, defaultValue = "false")
  private boolean favorite;

  @Ignore
  private List<Step> steps = new ArrayList<>();

  @Ignore
  private List<Ingredient> ingredients = new ArrayList<>();

  /**
   * Recipe is the top-level {@link Entity} in the database.  It gets a non-editable {@link #url} if
   * it was loaded from a website.  {@link #title}, {@link #favorite} and the list of {@link Step}s
   * can be customized by the user.
   */
  public Recipe() {
  }

  @Ignore
  public Recipe(String url, @NotNull String title, boolean favorite, List<Step> steps, List<Ingredient> ingredients) {
    super();
    this.url = url;
    this.title = title;
    this.favorite = favorite;
    this.ingredients = ingredients;
    this.steps = steps;
  }

  public Recipe (RecipePojo recipePojo) {
    this.recipeId = recipePojo.getRecipe().getRecipeId();
    this.url = recipePojo.getRecipe().getUrl();
    this.title = recipePojo.getRecipe().getTitle();
    this.favorite = recipePojo.getRecipe().isFavorite();
    this.ingredients = this.getIngredients(recipePojo);
    this.steps = this.getSteps(recipePojo);
  }

  public Recipe (AssemblyRecipe data) {
    this.recipeId = data.getId();
    this.url = data.getUrl();
    this.title = data.getTitle();
    this.favorite = data.isFavorite();
    this.ingredients = data.getIngredients();
    this.steps = data.getSteps();
  }

  /* Stretch goal fields (nullable):
  private String meal;
  private Set<String> tag;
  private String image;
  */

  public long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(long recipeId) {
    this.recipeId = recipeId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Bindable
  public @NotNull String getTitle() {
    return title;
  }

  public void setTitle(@NotNull String title) {
    this.title = title;
    notifyPropertyChanged(BR.title);
  }

  @Bindable
  public boolean isFavorite() {
    return favorite;
  }

  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
    notifyPropertyChanged(BR.favorite);
  }

  public List<Step> getSteps() {
    return steps;
  }

  private List<Step> getSteps(RecipePojo recipePojo) {
    for (Step step : recipePojo.getSteps()) {
      this.setStep(step);
    }
    return this.steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  public void setStep(Step step) {
    if (this.steps == null) {
      this.steps = new ArrayList<>();
    }
    this.steps.add(step);
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  private List<Ingredient> getIngredients(RecipePojo recipePojo) {
    for (Ingredient ingredient : recipePojo.getIngredients()) {
      this.setIngredient(ingredient);
    }
    return this.ingredients;
  }

  public void setIngredients(
      List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public void setIngredient(Ingredient ingredient) {
    if (this.ingredients == null) {
      this.ingredients = new ArrayList<>();
    }
    this.ingredients.add(ingredient);
  }

  public static Recipe getEmptyRecipe() {
    Recipe recipe = new Recipe();
    recipe.setIngredients(Arrays
        .asList(new Ingredient(recipe.getRecipeId(), "1", Unit.OTHER, "", ""),
            new Ingredient(recipe.getRecipeId(), "1", Unit.OTHER, "", ""),
            new Ingredient(recipe.getRecipeId(), "1", Unit.OTHER, "", "")));
    recipe.setSteps(Arrays.asList(
        new Step(recipe.getRecipeId(), "", 1),
        new Step(recipe.getRecipeId(), "", 2),
        new Step(recipe.getRecipeId(), "", 3)
    ));
    return recipe;
  }


  @NonNull
  @Override
  public String toString() {
    return getTitle();
  }

  // DEV dummy recipes to pre-fill the database.

  @SuppressWarnings("SpellCheckingInspection")
  private static final Ingredient[] phonyIngredients = {
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
    String[] cupcakeIpsum = {"Cupcake ipsum dolor sit amet pastry jujubes. Chupa chups sugar plum cupcake toffee.",  "Donut tart marshmallow caramels halvah pie biscuit sesame snaps. Chocolate toffee biscuit oat cake cupcake pastry powder carrot cake tiramisu.", "Sesame snaps lollipop gummi bears danish bear claw macaroon pudding cotton candy cheesecake. Cheesecake cake dessert marshmallow icing.", "Wafer cookie jelly-o. Macaroon cotton candy croissant tart dessert. Lemon drops jelly danish powder soufflé candy canes pie.", "Jujubes carrot cake liquorice gummies pastry. Jujubes apple pie oat cake tart cake cupcake. Marzipan sugar plum sweet ice cream apple pie."};
    List<Step> fakeSteps = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Step step = new Step();
      step.setRecipeOrder(i+1);
      step.setInstructions("Follow instructions " + (5 - i) + " more times.  " + cupcakeIpsum[i]);
      fakeSteps.add(step);
    }
    return fakeSteps;
  }

  /**
   * This array populates the database with dummy recipes.
   *
   * @return a list of bogus recipe objects.
   */
  @SuppressWarnings("SpellCheckingInspection")
  public static Recipe[] populateData() {
    return new Recipe[]{
        new Recipe("www.cookies.com", "Cookies", false, fakeSteps(), Arrays.asList(phonyIngredients)),
        new Recipe("www.biscuits.com", "Biscuits", true, fakeSteps(), Arrays.asList(phonyIngredients)),
        new Recipe("www.grilledcheese.com", "Grilled Cheese", false, fakeSteps(), Arrays.asList(phonyIngredients)),
        new Recipe("www.spanakopita.com", "Spanakopita", false, fakeSteps(), Arrays.asList(phonyIngredients)),
        new Recipe("www.lutherburger.com", "Luther Burger", false, fakeSteps(), Arrays.asList(phonyIngredients)),
        new Recipe("www.chronwich.com", "The Chronwich", true, fakeSteps(), Arrays.asList(phonyIngredients)),
    };
  }
}
