package com.semartinez.projects.choppit.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupPrepper {

  private Document document;
  private Elements allElements;


  JsoupPrepper() {
  }

  public File prepare(String url) throws IOException {
    if (document == null) {
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
    doWork();
    // TODO: simplify.
    return new File(document.html());
  }

  private void doWork() {
    document.filter(new Strainer());
  }

  public Document getDocument() {
    return document;
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
