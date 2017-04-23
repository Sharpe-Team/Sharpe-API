package com.esgi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
	public List<UserDto> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map((user) -> {
					UserDto userDto = UserAdapter.entityToDto(user);
					// Hide password
					userDto.setPassword("");
					return userDto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public UserDto getUser(Long id) throws UserNotFoundException {
		UserEntity user = userRepository.findOne(id);

		if(user == null) {
			throw new UserNotFoundException();
		}

		return UserAdapter.entityToDto(user);
	}

	@Override
	public UserDto getUserByUsername(String username) throws UserNotFoundException {
		UserEntity userEntity = userRepository.findByFirstname(username)
				.stream()
				.findFirst()
				.orElseThrow(UserNotFoundException::new);
		return UserAdapter.entityToDto(userEntity);
	}

	@Override
	public UserDto insertUser(UserDto userDto) throws EmailAddressAlreadyExistsException {
		UserEntity userEntity = UserAdapter.dtoToEntity(userDto);

		// Check if the email address is already used
		try {
			getUserByUsername(userDto.getEmail());
			throw new EmailAddressAlreadyExistsException();
		} catch (UserNotFoundException e) {
			// Hash the password before save it in the DB
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
			userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

			userEntity = userRepository.save(userEntity);
		}

		return UserAdapter.entityToDto(userEntity);
	}
}
