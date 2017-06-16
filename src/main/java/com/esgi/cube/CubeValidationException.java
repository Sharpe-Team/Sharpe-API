package com.esgi.cube;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by thomasfouan on 16/06/2017.
 */
@ResponseStatus(BAD_REQUEST)
public class CubeValidationException extends RuntimeException {
}
