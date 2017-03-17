package com.esgi.message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by rfruitet on 08/03/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @InjectMocks
    MessageServiceImpl messageService;

    @Mock
    MessageRepository messageRepository;

    @Mock
    HttpSession httpSession;

    private MessageEntity message1;
    private MessageEntity message2;
    private MessageEntity message3;
    private MessageEntity message4;
    private MessageEntity message5;

    public void init() {
        message1 = MessageEntity.builder().id(1L)
                .topic(1L)
                .message("message1")
                .build();

        message2 = MessageEntity.builder().id(2L)
                .topic(1L)
                .message("message2")
                .build();

        message3 = MessageEntity.builder().id(3L)
                .topic(1L)
                .message("message3")
                .build();

        message4 = MessageEntity.builder().id(4L)
                .topic(1L)
                .message("message11")
                .build();

        message5 = MessageEntity.builder().id(5L)
                .topic(1L)
                .message("message5")
                .build();
    }

    @Before
    public void configure() {
        init();

        ArrayList<MessageEntity> messageEntities = new ArrayList<>();

        messageEntities.add(message1);
        messageEntities.add(message2);
        messageEntities.add(message3);
        messageEntities.add(message4);
        messageEntities.add(message5);

        when(messageRepository.findTop10ByTopic(1L)).thenReturn(messageEntities);
        when(httpSession.getAttribute("username")).thenReturn("myUsername");
    }

    @Test
    public void should_return_all_message_of_topic_one() {
        List<MessageDto> allMessages = messageService.getMessageForTopic(1L);

        assertThat(allMessages).hasSize(5);
    }

    @Test
    public void should_insert_one_message() {
        MessageDto messageDto = MessageDto.builder()
                .id(6L)
                .topic(1L)
                .message("my message")
                .build();

        MessageDto messageDto2 = messageService.insertMessage(messageDto);

        assertThat(messageDto2.getId()).isEqualTo(6L);
        assertThat(messageDto2.getTopic()).isEqualTo(1);
        assertThat(messageDto2.getUsername()).isEqualTo("myUsername");
        assertThat(messageDto2.getMessage()).isEqualTo("my message");

    }
}