package com.esgi.role;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@ResponseStatus(BAD_REQUEST)
public class RoleValidationException extends RuntimeException {
}
