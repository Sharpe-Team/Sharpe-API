package com.esgi.ruc;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@ResponseStatus(NOT_FOUND)
public class RucNotFoundException extends Exception {
}
