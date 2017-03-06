package com.esgi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserEntity getUser(Long id) throws UserNotFoundException {

		UserEntity user = userRepository.findOne(id);

		if(user == null) {
			throw new UserNotFoundException();
		}

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public UserEntity getUserByUsername(String username) throws UserNotFoundException {
		return userRepository.findByUsername(username).stream().findFirst().orElseThrow(UserNotFoundException::new);
	}

	@Override
	@Transactional
	public UserEntity insertUser(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}
}
