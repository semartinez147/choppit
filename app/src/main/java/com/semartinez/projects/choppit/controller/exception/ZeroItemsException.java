package com.semartinez.projects.choppit.controller.exception;

public class ZeroItemsException extends RuntimeException {

  public ZeroItemsException() {
  }

  public ZeroItemsException(String message) {
    super(message);
  }
}
