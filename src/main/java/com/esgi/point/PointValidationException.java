package com.esgi.point;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by rfruitet on 27/03/2017.
 */
@ResponseStatus(BAD_REQUEST)
public class PointValidationException extends RuntimeException {
}
