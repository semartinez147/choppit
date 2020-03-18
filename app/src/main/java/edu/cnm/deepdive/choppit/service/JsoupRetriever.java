package edu.cnm.deepdive.choppit.service;

import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Step;
import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupRetriever {

  // TODO add any new measurement enum values to the parentheses here.
  public static final String MAGIC_INGREDIENT_REGEX = "^([\\d\\W]*)\\s(tsp|teaspoon|tbsp|tablespoon|oz|ounce|c|cup*)*s??\\b(.*)";
  private Document document;
  private String url;
  private String ingredient;
  private String instruction;
  private String ingredientClass;
  private String instructionClass;
    private static List<String> listRawIngredients = new ArrayList<>();
  private static List<String> measurements = new ArrayList<>();
  private static List<String> units = new ArrayList<>();
  private static List<String> names = new ArrayList<>();
  private static List<String> listInstructions = new ArrayList<>();
  private List<Step> steps = new ArrayList<>();
  private List<Ingredient> ingredients = new ArrayList<>();

  public static JsoupRetriever getInstance() {
    return InstanceHolder.INSTANCE;
  }

  // TODO send data to database
  private void getData() throws IOException {
    setValues(); // pull url, ingredient & step from MVM
    getPage(); // attempt to retrieve url through jsoup
    ingredientClass = getSourceClass(ingredient); // identify wrapper classes
    instructionClass = getSourceClass(instruction);
    listRawIngredients = getClassContents(ingredientClass); // list all ingredients
    ingredientParse(listRawIngredients); // parse ingredients
    listInstructions = getClassContents(instructionClass); // list all instructions
  }

  private void setValues() {
    url = MainViewModel.getUrl();
    ingredient = MainViewModel.getIngredient();
    instruction = MainViewModel.getStep();
  }

  private void getPage() throws IOException {
    try {
      document = Jsoup.connect(url).get();
    } catch (IOException e) {
      e.printStackTrace(); // TODO something useful
    }
  }

  private String getSourceClass(String text) {
    Elements e = document.select(String.format("*:containsOwn(%s)", text));
    // TODO error handling (no matching text just returns an empty List).

    return e.get(0).attr("class");
  }

  private List<String> getClassContents(String klass) {
    Elements e = document.getElementsByClass(klass);

    return e.eachText();
  }

  private void stepBuilder(List<String> instructions) {
    for (int i = 0; i < instructions.size(); i++) {
      Step step = new Step();
      step.setRecipeOrder(i + 1);
      step.setInstructions(instructions.get(i));
      steps.add(step);
      }
  }

  private void ingredientBuilder(List<String> listRawIngredients) {
    Pattern pattern = Pattern.compile(MAGIC_INGREDIENT_REGEX);
    for (String rawIngredient : listRawIngredients) {
      Matcher matcher = pattern.matcher(ingredient);
      Ingredient ingredient = new Ingredient();
      if (matcher.find()) {
        ingredient.setQuantity(matcher.group(1));
        ingredient.setUnit(Ingredient.Unit.toUnit(matcher.group(2)));
        ingredient.setName(matcher.group(3));
      }
      ingredients.add(ingredient);
    }
  }

  private void ingredientParse(List<String> listRawIngredients) {
    Pattern pattern = Pattern.compile(MAGIC_INGREDIENT_REGEX);
    for (String ingredient : listRawIngredients) {
      Matcher matcher = pattern.matcher(ingredient);
      if (matcher.find()) {
        measurements.add(matcher.group(1));
        units.add(matcher.group(2));
        names.add(matcher.group(3));
      }
    }
  }

  public String getIngredientClass() {
    return ingredientClass;
  }

  public String getInstructionClass() {
    return instructionClass;
  }

  public static List<String> getListRawIngredients() {
    return listRawIngredients;
  }

  public static List<String> getMeasurements() {
    return measurements;
  }

  public static List<String> getUnits() {
    return units;
  }

  public static List<String> getNames() {
    return names;
  }

  public static List<String> getListInstructions() {
    return listInstructions;
  }

  private static class InstanceHolder {

    private static final JsoupRetriever INSTANCE = new JsoupRetriever();
  }

}
