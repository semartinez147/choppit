package com.semartinez.projects.choppit.service;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeFilter;

/**
 * This class was written before I had any knowledge of HTML structure and basically uses brute
 * force to eliminate anything that doesn't look like potentially useful text.  The first item on my
 * to-do list is to write a new version that just selects ordered & unordered lists, but this
 * iteration may be useful as a fallback for weirdly-formatted websites.
 */
public class Strainer implements NodeFilter {

  @Override
  public FilterResult head(Node node, int i) {
    if (!(node instanceof TextNode) && node.childNodes().isEmpty()) {
      return FilterResult.REMOVE;
    } else {
      if (node.attributes().toString().contains("href")
          || node.attributes().toString().contains("Icon")
          || node.attributes().toString().contains("Media")
          || node.attributes().toString().contains("Container")
          || node.attributes().toString().contains("Social")
          || node.attributes().toString().contains("Header")
          || node.attributes().toString().contains("Footer")
          || node.attributes().toString().contains("Comment")
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
}