package com.semartinez.projects.choppit.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupPrepper {

  private Document document;
  private Elements allElements;


  JsoupPrepper() {
  }

  // Refactoring as DocumentWithStrings instead of File
  public DocumentWithStrings prepare(String url) throws IOException {
    if (document == null) {
      throw new NullPointerException();
      // Call reconnect if the breakpoint is ever triggered.
    }
    doWork();
    // TODO: simplify.
    Set<String> strings = new HashSet<>(document.getAllElements().eachText());
    return new DocumentWithStrings(url, document, strings);
  }

  private void reconnect(String url) throws IOException {
    try {
      document = Jsoup.connect(url).get();
    } catch (MalformedURLException e) {
      throw new MalformedURLException("Not a valid link");
    } catch (HttpStatusException e) {
      throw new IOException("There was a problem with the website.");
    } catch (IOException e) {
      throw new IOException("An unknown error occurred.  Please try again.");
    }
  }

  private void doWork() {
    document.filter(new Strainer());
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  public static JsoupPrepper getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {

    private static final JsoupPrepper INSTANCE = new JsoupPrepper();
  }
}
