package com.semartinez.projects.choppit.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jsoup.nodes.Document;

public class DocumentWithStrings extends Document {

  private final Document document;
  private final List<String> strings;
  private String url;
  private String title;

  public DocumentWithStrings(String baseUri, Document doc, List<String> strings) {
    super(baseUri);
    document = doc;
    url = baseUri;
    this.strings = strings;
  }

  public List<String> getStrings() {
    return new ArrayList<>(new HashSet<>(strings));
  }

  public Document getDocument() {
    return document;
  }

  private class Comparator implements java.util.Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
      return o1.length() - o2.length();
    }
  }
}
