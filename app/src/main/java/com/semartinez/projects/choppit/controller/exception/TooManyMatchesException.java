package com.semartinez.projects.choppit.controller.exception;

/**
 * TooManyMatchesException handles errors thrown by {@link com.semartinez.projects.choppit.service.JsoupMachine}
 * when text selected by the user is found in more than one HTML element.
 */
public class TooManyMatchesException extends RuntimeException {


  /**
   * @param s indicates whether the exception came from parsing HTML for an ingredient or
   *          instruction string.
   */
  public TooManyMatchesException(String s) {
    super("Found too many matches for that " + s + ". Please paste more text.");
  }
}
