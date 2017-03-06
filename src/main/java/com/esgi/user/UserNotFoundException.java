package com.esgi.user;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@ResponseStatus(NOT_FOUND)
public class UserNotFoundException extends Exception {
}
