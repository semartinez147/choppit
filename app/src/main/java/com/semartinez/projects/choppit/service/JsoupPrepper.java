package com.semartinez.projects.choppit.service;

import java.io.File;
import java.io.IOException;
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
      connect(url);
    }
    doWork();
    return getHtml();
  }

  private void connect(String url) throws IOException {
    document = Jsoup.connect(url).get();
  }

  private void doWork() {
    // TODO Debug this process
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
  private File getHtml() {
    return new File(allElements.first().html());
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
