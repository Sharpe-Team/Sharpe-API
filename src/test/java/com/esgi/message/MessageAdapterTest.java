package com.esgi.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rfruitet on 08/03/2017.
 */
@RunWith(SpringRunner.class)
public class MessageAdapterTest {

    @InjectMocks
    MessageAdapter messageAdapter;

    @Test
    public void should_return_an_message_entity() {
        MessageDto messageDto = MessageDto.builder()
                .id(1L)
                .username("first")
                .message("message")
                .build();

        MessageEntity messageEntity = messageAdapter.convertToEntity(messageDto);

        assertThat(messageEntity.getUsername()).isEqualTo("first");
        assertThat(messageEntity.getId()).isEqualTo(1L);
        assertThat(messageEntity.getMessage()).isEqualTo("message");
    }

    @Test
    public void should_return_an_user_dto() {
        MessageEntity messageEntity = MessageEntity.builder()
                .id(1L)
                .username("first")
                .message("message")
                .build();

        MessageDto messageDto = messageAdapter.convertToDto(messageEntity);

        assertThat(messageDto.getId()).isEqualTo(1L);
        assertThat(messageDto.getUsername()).isEqualTo("first");
        assertThat(messageDto.getMessage()).isEqualTo("message");
    }
}
