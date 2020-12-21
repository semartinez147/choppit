package com.semartinez.projects.choppit.controller.exception;

public class ZeroMatchesException extends RuntimeException {

  public ZeroMatchesException() {
    super("Found no extractable text matching that ");
  }

  public ZeroMatchesException(String message) {
    super(message);
  }
}
