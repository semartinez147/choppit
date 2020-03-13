package edu.cnm.deepdive.choppit.service;

import edu.cnm.deepdive.choppit.viewmodel.MainViewModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
  private static Elements ingredients;
  private static List<String> listIngredients;
  private static List<String> listSteps;

  public void setValues() {
    url = MainViewModel.getUrl();
    ingredient = MainViewModel.getIngredient();
    step = MainViewModel.getStep();
  }

  public void getPage() throws IOException {
    try {
      document = Jsoup.connect(url).get();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Elements getSourceClass(String text) {
    String query = "\"p:contains(%s)\"";
    query = String.format(query, text);
    return document.select(query); // TODO test this
  }

  public Elements getSourceClassAlt(String text) {
    return document.select(String.format("\"p:contains(%s)\"", text)); // TODO test this too
  }
  
  // TODO split ingredient to measure-unit-name & send to database
  public void get(String url) {

    listIngredients.addAll(getSourceClass(ingredient).eachText());
    listSteps.addAll(getSourceClass(step).eachText());

    }
}
