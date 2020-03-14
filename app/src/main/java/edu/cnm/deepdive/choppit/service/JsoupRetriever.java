package edu.cnm.deepdive.choppit.service;

import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupRetriever {

  // TODO add any new measurement enum values to the parentheses here.
  public static final String MAGIC_REGEX = "^([\\d\\W]*)\\s(tsp|teaspoon|tbsp|tablespoon|oz|ounce|c|cup*)*s??\\b(.*)";
  private Pattern sorter = Pattern.compile(MAGIC_REGEX);
  private Document document;
  private String url;
  private String ingredient;
  private String step;
  private String ingredientClass;
  private String stepClass;
  private static List<String> listIngredients;
  private static List<String> listSteps;

  public static JsoupRetriever getInstance() {
    return InstanceHolder.INSTANCE;
  }

  // TODO split ingredient to measure-unit-name & send to database
  private void getData() throws IOException {
    setValues();
    getPage();
    ingredientClass = getSourceClass(ingredient);
    stepClass = getSourceClass(step);
    listIngredients = getClassContents(ingredientClass);
    listSteps = getClassContents(stepClass);
  }

  private void setValues() {
    url = MainViewModel.getUrl();
    ingredient = MainViewModel.getIngredient();
    step = MainViewModel.getStep();
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

  public String getIngredientClass() {
    return ingredientClass;
  }

  public void setIngredientClass(String ingredientClass) {
    this.ingredientClass = ingredientClass;
  }

  public String getStepClass() {
    return stepClass;
  }

  public void setStepClass(String stepClass) {
    this.stepClass = stepClass;
  }

  public static List<String> getListIngredients() {
    return listIngredients;
  }

  public static List<String> getListSteps() {
    return listSteps;
  }

  private static class InstanceHolder {

    private static final JsoupRetriever INSTANCE = new JsoupRetriever();
  }

}
