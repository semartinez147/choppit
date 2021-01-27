package com.semartinez.projects.choppit.service;

import java.util.Set;
import org.jsoup.nodes.Document;

public class DocumentWithStrings extends Document {

  private final Document document;
  private final Set<String> strings;
  private String url;
  private String title;

  public DocumentWithStrings(String baseUri, Document doc, Set<String> strings) {
    super(baseUri);
    document = doc;
    this.strings = strings;
  }

  public Set<String> getStrings() {
    return strings;
  }

  public Document getDocument() {
    return document;
  }
}
