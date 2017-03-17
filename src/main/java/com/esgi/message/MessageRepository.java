package com.esgi.message;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rfruitet on 07/03/2017.
 */
@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {

	List<MessageEntity> findAll();

	List<MessageEntity> findTop10ByTopic(Long topic);

	MessageEntity save(MessageEntity messageEntity);

	void delete(Long idMessage);
}