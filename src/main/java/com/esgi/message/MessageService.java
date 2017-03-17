package com.esgi.message;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rfruitet on 08/03/2017.
 */
public interface MessageService {

    @Transactional(readOnly = true)
    List<MessageDto> getAllMessages();

    @Transactional(readOnly = true)
    List<MessageDto> getMessageForTopic(Long idTopic) throws MessageException;

    @Transactional(readOnly = true)
    MessageDto insertMessage(MessageDto messageDto);

    @Transactional(readOnly = true)
    void deleteMessage(Long idMessage);
}
