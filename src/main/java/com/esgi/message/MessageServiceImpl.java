package com.esgi.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rfruitet on 08/03/2017.
 */

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private HttpSession userSession;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, HttpSession userSession) {
        this.messageRepository = messageRepository;
        this.userSession = userSession;
    }

    @Override
    public List<MessageDto> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(MessageAdapter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getMessageForTopic(Long idTopic) throws MessageException {
        return messageRepository.findTop10ByTopic(idTopic)
                .stream()
                .map(MessageAdapter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto insertMessage(MessageDto messageDto) {
        messageDto.setUsername((String) userSession.getAttribute("username"));
        MessageEntity messageEntity = messageRepository.save(MessageAdapter.convertToEntity(messageDto));
        return messageDto;
    }

    @Override
    public void deleteMessage(Long idMessage) {
        messageRepository.delete(idMessage);
    }
}
