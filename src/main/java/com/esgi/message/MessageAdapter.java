package com.esgi.message;

/**
 * Created by rfruitet on 08/03/2017.
 */

import org.springframework.stereotype.Component;

@Component
public class MessageAdapter {

    public static MessageDto convertToDto(MessageEntity messageEntity) {
        return MessageDto.builder()
                .id(messageEntity.getId())
                .topic(messageEntity.getTopic())
                .username(messageEntity.getUsername())
                .message(messageEntity.getMessage())
                .build();
    }

    public static MessageEntity convertToEntity(MessageDto messageDto) {
        return MessageEntity.builder()
                .id(messageDto.getId())
                .topic(messageDto.getTopic())
                .username(messageDto.getUsername())
                .message(messageDto.getMessage())
                .build();
    }
}
