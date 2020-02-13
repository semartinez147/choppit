package edu.cnm.deepdive.choppit.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RecipeGetter {

  public static void getRecipe(String url) {
    url = "https://www.foodnetwork.com/recipes/alton-brown/the-chewy-recipe-1909046";
    Document document;
    try {
      document = Jsoup
          .connect(url)
          .get();
      Elements ingredients = document.select(".o-Ingredients__a-Ingredient");
      Elements steps = document.select(".o-Method__m-Step");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
