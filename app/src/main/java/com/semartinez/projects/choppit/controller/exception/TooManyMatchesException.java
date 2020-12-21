package com.semartinez.projects.choppit.controller.exception;

public class TooManyMatchesException extends RuntimeException {

  public TooManyMatchesException() {
    super("Found too many matches. Please paste more text.");
  }

  public TooManyMatchesException(String s) {
    super("Found too many matches for that " + s + ". Please paste more text.");
  }
}
