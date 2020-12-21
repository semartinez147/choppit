package com.semartinez.projects.choppit.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter;

public class JsoupPrepper {
  private Document document;
  private Elements allElements;


  JsoupPrepper() {
  }

  public File prepare(String url) throws IOException {
    if (document == null) {
      try {
        document = Jsoup.connect(url).get();
      } catch (IOException e) {
        if (e instanceof MalformedURLException) {
          throw new MalformedURLException("Not a valid link");
        } else if (e instanceof HttpStatusException) {
          throw new IOException("There was a problem with the website.");
        } else {
          throw new IOException("An unknown error occurred.  Please try again.");
        }
      }
    }
    doWork();
    return new File(allElements.first().html());
  }

  private void doWork() {
    // TODO Debug this process - doing extra work
    allElements = document.getAllElements().filter(new NodeFilter() {
      @Override
      public FilterResult head(Node node, int i) {
        if (!(node instanceof TextNode) && node.childNodes().isEmpty()) {
          return FilterResult.REMOVE;
        } else {
          if (node.attributes().toString().contains("href")
              || node.attributes().toString().contains("Icon")
              || node.attributes().toString().contains("Social")
              || node.attributes().toString().contains("Header")
              || node.attributes().toString().contains("Footer")
          ) {
            return FilterResult.REMOVE;
          }
        }
        return FilterResult.CONTINUE;
      }
      @Override
      public FilterResult tail(Node node, int i) {
        if (!(node instanceof TextNode) && node.childNodes().isEmpty()) {
          return FilterResult.REMOVE;
        } else {
          return FilterResult.CONTINUE;
        }
      }
    });

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
