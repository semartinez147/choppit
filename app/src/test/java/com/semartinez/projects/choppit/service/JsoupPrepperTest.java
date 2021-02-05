package com.semartinez.projects.choppit.service;

import static org.junit.Assert.*;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class JsoupPrepperTest {

  JsoupPrepper prepper = new JsoupPrepper();

  @Test
  public void prepare() throws IOException {

    prepper.setDocument(Jsoup.parse(new File("src/test/test-data/Mac-and-Cheese.html"), "UTF-8"));
    Document expected = Jsoup.parse(new File("src/test/test-data/filtered.html"), "UTF-8");
    Document prepped = prepper.prepare("noUrl");
    assertEquals(expected.toString(), prepped.toString());
  }
}