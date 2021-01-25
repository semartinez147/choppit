package com.semartinez.projects.choppit.service;

import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.concurrent.Callable;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupPrepper {

  private Document document;

  JsoupPrepper() {
  }


  public Document prepare(String url) throws IOException {
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
//    document.outputSettings(new OutputSettings().prettyPrint(false));
//    document.filter(new Strainer());
    document = Jsoup.parse(document.select("div:not(:has(div))").select("div:matches(.)").toString());
    return document;
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
