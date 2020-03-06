package edu.cnm.deepdive.choppit.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RecipeGetter {

  // TODO add any new measurement enum values to the parentheses here.
  public static final String MAGIC_REGEX = "^([\\d\\W]*)\\s(tsp|teaspoon|tbsp|tablespoon|oz|ounce|c|cup*)*s??\\b(.*)";

  // TODO make this actually work.
  public static void getRecipe(String url) {
    url = "https://www.foodnetwork.com/recipes/alton-brown/the-chewy-recipe-1909046";
    Document document;
    try {
      document = Jsoup
          .connect(url)
          .get();
      Elements ingredients = document.select(".o-Ingredients__a-Ingredient");

      Elements elements = document.getElementsByClass("o-Ingredients");
      elements.text(); // turns it into text
     // HtmlCompat.fromHtml(elements.html(),HtmlCompat.FROM_HTML_MODE_LEGACY); // display in HTML formatting

      Elements steps = document.select(".o-Method__m-Step");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
