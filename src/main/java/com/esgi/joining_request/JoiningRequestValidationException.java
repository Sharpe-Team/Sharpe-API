package com.esgi.joining_request;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by thomasfouan on 10/07/2017.
 */
@ResponseStatus(BAD_REQUEST)
public class JoiningRequestValidationException extends RuntimeException {
}
