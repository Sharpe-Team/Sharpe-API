package com.esgi.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rfruitet on 07/03/2017.
 */

@RunWith(SpringRunner.class)
public class UserAdapterTest {

    @InjectMocks
    UserAdapter userAdapter;

    @Test
    public void should_return_an_user_entity() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("first")
                .password("password")
                .build();

        UserEntity userEntity = userAdapter.dtoToEntity(userDto);

        assertThat(userEntity.getId()).isEqualTo(1L);
        assertThat(userEntity.getUsername()).isEqualTo("first");
        assertThat(userEntity.getPassword()).isEqualTo("password");
    }

    @Test
    public void should_return_an_user_dto() {
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("first")
                .password("password")
                .build();

        UserDto userDto = userAdapter.entityToDto(userEntity);

        assertThat(userDto .getId()).isEqualTo(1L);
        assertThat(userDto .getUsername()).isEqualTo("first");
        assertThat(userDto .getPassword()).isEqualTo("password");
    }
}
