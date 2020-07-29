package com.semartinez.projects.choppit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;


public class JsoupRetrieverTest {

  static Document mac;
  static Document chewy;
  static Document turkey;
  static List<Document> docs = new LinkedList<>();

  static {
    try {
      mac = Jsoup.parse(new File("Baked Macaroni and Cheese Recipe _ Alton Brown _ Food Network.html"), "UTF-8", "https://www.foodnetwork.com/");
      chewy = Jsoup.parse(new File("The Chewy Recipe _ Alton Brown _ Food Network.html"), "UTF-8", "https://www.foodnetwork.com/");
      turkey = Jsoup.parse(new File("Good Eats Roast Turkey Recipe _ Alton Brown _ Food Network.html"), "UTF-8", "https://www.foodnetwork.com/");
    } catch (IOException e) {
      e.printStackTrace();
    }
    docs.add(mac);
    docs.add(chewy);
    docs.add(turkey);
  }

  JsoupRetriever retriever = new JsoupRetriever();

  @ParameterizedTest
  @DisplayName("Returns HTML class based on text input")
  public void testGetClass() {
    for (Document doc : docs) {
      assertEquals("", "");
    }
  }

  @ParameterizedTest
  @DisplayName("Returns list of steps/ingredients based on HTML class")
  public void testGetClassContents() {
  }

  @ParameterizedTest
  @DisplayName("Returns list of steps by parsing step class strings")
  public void testBuildSteps() {
  }

  @ParameterizedTest
  @DisplayName("Returns list of ingredients by parsing ingredient class strings")
  public void testBuildIngredients() {
    assertNotNull(retriever.buildIngredients());
  }
}