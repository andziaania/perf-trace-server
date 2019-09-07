package com.pawelczyk.perftraceserver.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ania.pawelczyk
 * @since 07.09.2019.
 */
@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY, reason="Wrong parameter")  // 404
public class WrongParameterException extends RuntimeException {
  public WrongParameterException(String message) {
    super(message);
  }
}
