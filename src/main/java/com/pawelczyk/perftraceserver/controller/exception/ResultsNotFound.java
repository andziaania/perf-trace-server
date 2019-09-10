package com.pawelczyk.perftraceserver.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ania.pawelczyk
 * @since 10.09.2019.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Wrong parameter")  // 404
public class ResultsNotFound extends RuntimeException {
  public ResultsNotFound(String message) {
    super(message);
  }
}
