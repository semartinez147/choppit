package com.semartinez.projects.choppit.service;

import android.util.Log;
import com.semartinez.projects.choppit.controller.exception.TooManyMatchesException;
import com.semartinez.projects.choppit.controller.exception.ZeroMatchesException;
import com.semartinez.projects.choppit.model.entity.AssemblyRecipe;
import com.semartinez.projects.choppit.model.entity.Ingredient;
import com.semartinez.projects.choppit.model.entity.Ingredient.Unit;
import com.semartinez.projects.choppit.model.entity.Step;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * JsoupMachine does all the heavy lifting in the backend.  The first step after retrieving a
 * document in the {@link com.semartinez.projects.choppit.model.repository.RecipeRepository} is to
 * create a new {@link AssemblyRecipe}.  This object collects the output from the other methods here
 * so that it can be passed back to the frontend.  The raw HTML is filtered to eliminate things like
 * videos and ads, and the plain text of the remaining elements is added to the AssemblyRecipe and
 * passed back to the UI.  The {@code class} attribute of the Strings the user selects are used to
 * sort through the filtered HTML elements.  Everything with a matching {@code class} attribute is
 * collected in one of two lists.  Raw ingredients are separated using Regex into a measurement
 * quantity, measurement unit, and ingredient name, then converted to an {@link Ingredient}.  Recipe
 * instructions along with their position in the list create {@link Step}s.  The completed
 * AssemblyRecipe is then passed back to the UI for further editing by the user.
 */
public class JsoupMachine {

  // TODO add any new measurement enum values to the parentheses here.  Extract String to resource.
  public static final String MAGIC_INGREDIENT_REGEX = "^([\\d\\W]*)\\s(tsp|teaspoon|tbsp|tablespoon|oz|ounce|c|cup|lb|pound*)*s??\\b(.*)";
  private Document document;
  private List<String> listRawIngredients = new ArrayList<>();
  private List<String> listInstructions = new ArrayList<>();
  private final List<Step> steps = new ArrayList<>();
  private final List<Ingredient> ingredients = new ArrayList<>();
  private AssemblyRecipe assemblyRecipe;

  JsoupMachine() {
  }

  /**
   * @return a singleton instance of this class.
   */
  public static JsoupMachine getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * This is the first method called in the processing chain.  It creates a new {@link
   * AssemblyRecipe} using the URL and title of the document, then initiates a callable that filters
   * HTML elements.
   *
   * @param doc is received from {@link com.semartinez.projects.choppit.model.repository.RecipeRepository}
   *            after a successful connection attempt.
   * @return a list of Strings extracted from the HTML.
   */
  public Single<List<String>> prepare(Document doc) {
    document = doc;
    assemblyRecipe = new AssemblyRecipe();
    assemblyRecipe.setUrl(document.location());
    assemblyRecipe.setTitle(document.title());
    return Single.fromCallable(new CallMe());
  }

  /**
   * This method sets up two threads; one extracts ingredient text and the other extracts
   * instruction text.  Both are processed into Ingredient and Step objects. When both have
   * finished, the resulting Ingredients and Steps are added to an AssemblyRecipe.
   *
   * @param ingredient  received from user input on the SelectionFragment.
   * @param instruction received from user input on the SelectionFragment.
   * @return an AssemblyRecipe object containing
   */
  public AssemblyRecipe process(String ingredient, String instruction) {

    RunIngredients i = new RunIngredients(ingredient);
    RunSteps s = new RunSteps(instruction);

    Thread iThread = new Thread(i, "iThread");
    Thread sThread = new Thread(s, "sThread");

    iThread.start();
    sThread.start();

    try {
      iThread.join();
      sThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }

    assemblyRecipe.setIngredients(ingredients);
    assemblyRecipe.setSteps(steps);
    assemblyRecipe.setId(0);

    return assemblyRecipe;
  }

  /**
   * CallMe is a nested class to improve readability and encapsulation.
   */
  private class CallMe implements Callable<List<String>> {

    @Override
    public List<String> call() throws Exception {
      if (document == null) {
        throw new NullPointerException();
        // Call reconnect if the breakpoint is ever triggered.
      }
      document.filter(new Strainer());
      List<String> strings = document.getAllElements().parallelStream().map(Element::ownText)
          .distinct()
          .collect(Collectors.toList());
      if (strings.isEmpty()) {
        throw new ZeroMatchesException();
      }

      return strings;
    }
  }

  /**
   * RunIngredients is a nested class to improve readability and encapsulation.
   */
  private class RunIngredients implements Runnable {

    String ingredient;

    public RunIngredients(String ingredient) {
      this.ingredient = ingredient;
    }

    @Override
    public void run() {
      String ingredientClass = JsoupMachine.this.getClass(ingredient, "ingredient");
      listRawIngredients = getClassContents(ingredientClass); // list all ingredients
      buildIngredients();
    }
  }

  /**
   * RunSteps is a nested class to improve readability and encapsulation.
   */
  private class RunSteps implements Runnable {

    String instruction;

    public RunSteps(String instruction) {
      this.instruction = instruction;
    }

    @Override
    public void run() {
      String instructionClass = JsoupMachine.this.getClass(instruction, "step");
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
  protected String getClass(String text, String type) {
    Elements e = document.select(String.format("*:containsOwn(%s)", text));
    if (e.size() == 1) {
      return e.get(0).attr("class");
    } else {
      Log.e("Retriever failed:", "found " + e.size() + " matching classes");
      throw e.isEmpty() ? new ZeroMatchesException(type) : new TooManyMatchesException(type);
    }
  }

  /**
   * this method takes an HTML "class" attribute and compiles as {@link org.jsoup.nodes.Element}s
   * the contents of each matching HTML element.  Runs once on each text parameter.
   *
   * @param klass the values returned by {@link #getClass(String, String)}
   * @return the contents of each HTML element matching the provided "class" attribute as a {@link
   * String}.
   */
  protected List<String> getClassContents(String klass) {
    Elements e = document.getElementsByClass(klass);
    return e.eachText();
  }

  /**
   * Creates a Step out of an instruction String and its position (starting at 1) in the list.
   */
  protected void buildSteps() {
    for (int i = 0, j = 1; i < this.listInstructions.size(); i++, j++) {
      Step step = new Step();
      step.setRecipeOrder(j);
      step.setInstructions(this.listInstructions.get(i));
      steps.add(step);
    }
  }

  /**
   * Creates an Ingredient by separating the ingredient String into a quantity, unit, and name.
   */
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

  /**
   * @param document is passed from the RecipeRepository after a successful connection.
   */
  public void setDocument(Document document) {
    Log.d("Choppit", "Retriever document setter");
    this.document = document;
  }

  private static class InstanceHolder {

    private static final JsoupMachine INSTANCE = new JsoupMachine();
  }

}
