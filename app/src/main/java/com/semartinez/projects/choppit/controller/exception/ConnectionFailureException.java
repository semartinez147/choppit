package com.semartinez.projects.choppit.controller.exception;

public class ConnectionFailureException extends RuntimeException {

  public ConnectionFailureException() {
    super("Please check your internet connection and try again.");
  }

  public ConnectionFailureException(String s) {
    super(s);
  }
}
