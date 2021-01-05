package com.semartinez.projects.choppit.service;

import android.util.Log;
import com.semartinez.projects.choppit.controller.exception.*;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Ingredient.Unit;
import com.semartinez.projects.choppit.model.entity.Recipe.RecipeComponent;
import com.semartinez.projects.choppit.model.entity.Step;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupRetriever {

  // TODO add any new measurement enum values to the parentheses here.  Extract String to resource.
  public static final String MAGIC_INGREDIENT_REGEX = "^([\\d\\W]*)\\s(tsp|teaspoon|tbsp|tablespoon|oz|ounce|c|cup|lb|pound*)*s??\\b(.*)";
  private Document document;
  private List<String> listRawIngredients = new ArrayList<>();
  private List<String> listInstructions = new ArrayList<>();
  private final List<Step> steps = new ArrayList<>();
  private final List<Ingredient> ingredients = new ArrayList<>();

  JsoupRetriever() {
  }

  public static JsoupRetriever getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * This method coordinates the processing work by calling {@link #getKlass(String, String)}, followed by
   * {@link #getClassContents(String)} and {@link #buildIngredients()} / {@link #buildSteps()} then
   * matching {@link Ingredient}s to {@link Step}s.
   *
   * @param ingredient  parameter received from {@link com.semartinez.projects.choppit.controller.ui.home.HomeFragment}
   *                    user input via {@link com.semartinez.projects.choppit.viewmodel.MainViewModel}
   *                    and {@link com.semartinez.projects.choppit.model.repository.RecipeRepository}.
   * @param instruction parameter received from {@link com.semartinez.projects.choppit.controller.ui.home.HomeFragment}
   *                    *                    user input via {@link com.semartinez.projects.choppit.viewmodel.MainViewModel}
   *                    and *                    {@link com.semartinez.projects.choppit.model.repository.RecipeRepository}.
   * @return A {@link List} of {@link Step} objects with embedded {@link Ingredient}s.
   */
  public Map<String, List<? extends RecipeComponent>> process(String ingredient, String instruction) throws RuntimeException{
    //TODO Error handling: test getClass errors for 0 or >1 result.

    runIngredients i = new runIngredients(ingredient);
    runSteps s = new runSteps(instruction);

    Thread iThread = new Thread(i, "iThread");
    Thread sThread = new Thread(s, "sThread");

    iThread.start();
    sThread.start();

    try {
      iThread.join();
      sThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Map<String, List<? extends RecipeComponent>> data = new HashMap<>();

    data.put("ingredients", ingredients);
    data.put("steps", steps);

    return data;
  }

  private class runIngredients implements Runnable {
    String ingredient;
    public runIngredients(String ingredient) {
      this.ingredient = ingredient;
    }

    @Override
    public void run() throws RuntimeException {
      String ingredientClass = getKlass(ingredient, "ingredient");
      listRawIngredients = getClassContents(ingredientClass); // list all ingredients
      buildIngredients();
    }
  }

  private class runSteps implements Runnable {
    String instruction;
    public runSteps(String instruction) {
      this.instruction = instruction;
    }

    @Override
    public void run() throws RuntimeException{
      String instructionClass = getKlass(instruction, "step");
      listInstructions = getClassContents(instructionClass); // list all ingredients
      buildSteps();
    }
  }

  /**
   * This method searches the body of the HTML {@link Document} for the text of the given {@link
   * String}, and returns the "class" HTML attribute of the first element containing that text. Runs
   * once on each text parameter. This method needs to be improved to handle no or multiple matches
   * and request additional input from the user.
   *
   * @param text is either the ingredient or instruction text input by the user
   * @return the HTML "class" attribute enclosing the input {@link String}.
   */
  protected String getKlass(String text, String type) throws RuntimeException {
    Elements e = document.select(String.format("*:containsOwn(%s)", text));
    if (e.size() == 1) {
      return e.get(0).attr("class");
    } else {
      Log.e("Retriever failed:", "found " + e.size() + " matching classes");
      throw (e.size() == 0)? new ZeroMatchesException(type) :
          new TooManyMatchesException(type);
    }
  }

  /**
   * this method takes an HTML "class" attribute and compiles as {@link org.jsoup.nodes.Element}s
   * the contents of each matching HTML element.  Runs once on each text parameter.
   *
   * @param klass the values returned by {@link #getKlass(String, String)}
   * @return the contents of each HTML element matching the provided "class" attribute as a {@link
   * String}.
   */
  protected List<String> getClassContents(String klass) {
    Elements e = document.getElementsByClass(klass);
    return e.eachText();
  }

  protected void buildSteps() {
    for (int i = 0, j = 1; i < this.listInstructions.size(); i++, j++) {
      Step step = new Step();
      step.setRecipeOrder(j);
      step.setInstructions(this.listInstructions.get(i));
      steps.add(step);
    }
  }

  protected void buildIngredients() {
    Pattern pattern = Pattern.compile(MAGIC_INGREDIENT_REGEX);
    List<String> rawIngredients = this.listRawIngredients;
    for (String rawIngredient : rawIngredients) {
      Matcher matcher = pattern.matcher(rawIngredient);
      Ingredient ingredient = new Ingredient();
      if (matcher.find()) {
        // group 1 = measurement | group 2 = unit | group 3 = name
        ingredient.setQuantity(Objects.requireNonNull(matcher.group(1)));
        ingredient.setUnit(Unit.toUnit(matcher.group(2)));
        if (matcher.group(2) == null) {
          ingredient.setUnit(Unit.toUnit("other"));
          ingredient.setUnitAlt("");
        }
        ingredient.setName(Objects.requireNonNull(matcher.group(3)).trim());
      } else {
        ingredient.setQuantity("1");
        ingredient.setUnit(Unit.toUnit("other"));
        ingredient.setUnitAlt("");
        ingredient.setName(rawIngredient);
      }
      ingredients.add(ingredient);
    }
  }

  public void setDocument(Document document) {
    Log.d("Choppit", "Retriever document setter");
    this.document = document;
  }

  private static class InstanceHolder {

    private static final JsoupRetriever INSTANCE = new JsoupRetriever();
  }

}
