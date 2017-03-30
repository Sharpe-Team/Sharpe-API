package com.esgi.user;

import org.springframework.stereotype.Component;

/**
 * Created by rfruitet on 07/03/2017.
 */

@Component
public class UserAdapter {

    public static UserEntity dtoToEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .profilePicture(userDto.getProfilePicture())
                .created(userDto.getCreated())
                .updated(userDto.getUpdated())
                .build();
    }

    public static UserDto entityToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .profilePicture(userEntity.getProfilePicture())
                .created(userEntity.getCreated())
                .updated(userEntity.getUpdated())
                .build();
    }
}
