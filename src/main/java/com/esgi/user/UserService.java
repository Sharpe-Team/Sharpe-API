package com.esgi.user;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 04/03/2017.
 */
public interface UserService {

	@Transactional(readOnly = true)
	List<UserDto> getAllUsers();

	@Transactional(readOnly = true)
	UserDto getUser(Long id) throws UserNotFoundException;

	@Transactional(readOnly = true)
	UserDto getUserByUsername(String username) throws UserNotFoundException;

	@Transactional
	UserDto insertUser(UserDto userDto) throws EmailAddressAlreadyExistsException;

	@Transactional
	void deleteUser(Long id);
}
