package com.esgi.user;

import org.springframework.stereotype.Component;

/**
 * Created by rfruitet on 07/03/2017.
 */

@Component
public class UserAdapter {

    public UserEntity dtoToEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }

    public UserDto entityToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }
}
