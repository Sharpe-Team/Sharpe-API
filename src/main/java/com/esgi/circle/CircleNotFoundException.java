package com.esgi.circle;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Exception while accessing to a Circle that doesn't exist.
 */
@ResponseStatus(NOT_FOUND)
public class CircleNotFoundException extends Exception {
}
