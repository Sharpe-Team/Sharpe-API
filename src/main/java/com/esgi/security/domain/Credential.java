package com.esgi.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rfruitet on 05/04/2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    private String username;
    private String password;
}
