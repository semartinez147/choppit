package com.semartinez.projects.choppit.controller.exception;

public class ZeroMatchesException extends RuntimeException {

  public ZeroMatchesException() {
    super("Found no matching extractable text.");
  }

  public ZeroMatchesException(String s) {
    super("Found no extractable text matching that " + s + ".");
  }
}
