package com.esgi.message;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Created by rfruitet on 14/03/2017.
 */
@ResponseStatus(NOT_FOUND)
public class TopicNotFoundException extends Exception {
}
