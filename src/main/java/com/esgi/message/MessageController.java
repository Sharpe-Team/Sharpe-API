package com.esgi.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@RestController
@RequestMapping("/messages")
@CrossOrigin
public class MessageController {

	private MessageService messageService;

	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping
	public List<MessageDto> getMessageOfTopic(@RequestParam("topic") Long id) {
		return messageService.getMessageForTopic(id);
	}
}
