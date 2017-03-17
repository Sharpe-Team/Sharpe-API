package com.esgi.user;

import lombok.Builder;
import lombok.Data;

/**
 * Created by rfruitet on 07/03/2017.
 */

@Data
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String password;
}
