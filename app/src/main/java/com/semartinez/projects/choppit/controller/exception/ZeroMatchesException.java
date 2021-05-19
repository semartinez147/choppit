package com.semartinez.projects.choppit.controller.exception;

import org.jsoup.nodes.Document;

/**
 * ZeroMatchesException handles errors thrown by {@link com.semartinez.projects.choppit.service.JsoupMachine}
 * when {@link com.semartinez.projects.choppit.service.JsoupMachine#prepare(Document)} fails to
 * extract any text elements, or the text selected by the user is not found in the HTML.
 */
public class ZeroMatchesException extends RuntimeException {

  /**
   * The default constructor is used when the initial processing fails to find useful text in the
   * page at the user-provided URL.
   */
  public ZeroMatchesException() {
    super("Found no matching extractable text.");
  }

  /**
   * @param s indicates whether the exception came from parsing HTML for an ingredient or
   *          instruction string.
   */
  public ZeroMatchesException(String s) {
    super("Found no extractable text matching that " + s + ".");
  }
}
