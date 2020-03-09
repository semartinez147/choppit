package edu.cnm.deepdive.choppit.service;

import java.io.IOException;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RecipeGetter {

  // TODO add any new measurement enum values to the parentheses here.
  public static final String MAGIC_REGEX = "^([\\d\\W]*)\\s(tsp|teaspoon|tbsp|tablespoon|oz|ounce|c|cup*)*s??\\b(.*)";
  private Pattern sorter = Pattern.compile(MAGIC_REGEX);
  private static Elements ingredients;
  private static String[] listIngredients;

  // TODO split ingredient to measure-unit-name & send to database
  // TODO get steps.
  public static void getRecipe(String url) {
    url = "https://www.foodnetwork.com/recipes/alton-brown/the-chewy-recipe-1909046";
    int numIngredients;
    Document document;
    try {
      document = Jsoup
          .connect(url)
          .get();
      numIngredients = document.getElementsByClass("o-Ingredients__a-Ingredient").size();
      listIngredients = new String[numIngredients];
      for (int i = 0; i < numIngredients; i++) {
        listIngredients[i] = document.getElementsByClass("o-Ingredients__a-Ingredient").get(i).text();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
