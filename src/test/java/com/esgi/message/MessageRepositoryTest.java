package com.esgi.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by rfruitet on 07/03/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@MessageData
//@AutoConfigureTestDatabase(connection = H2)
@ActiveProfiles("test")
public class MessageRepositoryTest {

	@Autowired
	private MessageRepository messageRepository;

	@Test
	public void should_return_last_tree_message_of_topic_one() {
		List<MessageEntity> messageEntities = messageRepository.findTop10ByTopic(1L);

		System.out.println(messageEntities.toString());

		assertThat(messageEntities).hasSize(3);
		assertThat(messageEntities.get(0).getId()).isEqualTo(1);
		assertThat(messageEntities.get(0).getUsername()).isEqualTo("first");
		assertThat(messageEntities.get(0).getMessage()).isEqualTo("message1");
	}

	@Test
	public void should_insert_new_message() {
		MessageEntity messageEntity = MessageEntity.builder().id(11L)
				.topic(6L)
				.username("eleventh")
				.message("message11")
				.build();

		MessageEntity entity = messageRepository.save(messageEntity);

		assertThat(entity.getId()).isEqualTo(11);
		assertThat(entity.getTopic()).isEqualTo(6);
		assertThat(entity.getUsername()).isEqualTo("eleventh");
		assertThat(entity.getMessage()).isEqualTo("message11");
	}

	@Test
	public void should_delete_the_second_message() {
		messageRepository.delete(2L);

		List<MessageEntity> messageEntities = messageRepository.findTop10ByTopic(1L);

		assertThat(messageEntities).hasSize(2);
		assertThat(messageEntities.get(1).getId()).isEqualTo(3);
		assertThat(messageEntities.get(1).getUsername()).isEqualTo("third");
		assertThat(messageEntities.get(1).getMessage()).isEqualTo("message3");
	}
}
