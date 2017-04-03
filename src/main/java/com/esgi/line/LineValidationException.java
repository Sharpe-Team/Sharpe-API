package com.esgi.line;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by thomasfouan on 29/03/2017.
 */
@ResponseStatus(BAD_REQUEST)
public class LineValidationException extends RuntimeException {
}
