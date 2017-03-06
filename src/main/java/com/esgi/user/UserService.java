package com.esgi.user;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 04/03/2017.
 */
public interface UserService {
	@Transactional(readOnly = true)
	List<UserEntity> getAllUsers();

	@Transactional(readOnly = true)
	UserEntity getUser(Long id) throws UserNotFoundException;

	@Transactional(readOnly = true)
	UserEntity getUserByUsername(String username) throws UserNotFoundException;

	@Transactional
	UserEntity insertUser(UserEntity userEntity);
}
