package com.semartinez.projects.choppit.controller.exception;

import java.io.IOException;

public class TooManyItemsException extends RuntimeException {

  public TooManyItemsException() {
  }

  public TooManyItemsException(String message) {
    super(message);
  }
}
