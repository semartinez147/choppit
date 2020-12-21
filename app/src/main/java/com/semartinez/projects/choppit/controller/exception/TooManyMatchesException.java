package com.semartinez.projects.choppit.controller.exception;

public class TooManyMatchesException extends RuntimeException {

  public TooManyMatchesException() {
    super("Found too many matches. Please paste more text.");
  }

  public TooManyMatchesException(String message) {
    super(message);
  }
}
