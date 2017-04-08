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
	@Transactional(readOnly = true)
	public List<UserDto> getAllUsers() {
		List<UserDto> userDtoList = userRepository.findAll()
				.stream()
				.map((user) -> {
					UserDto userDto = UserAdapter.entityToDto(user);
					// Hide password
					userDto.setPassword("");
					return userDto;
				})
				.collect(Collectors.toList());

		return userDtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDto getUser(Long id) throws UserNotFoundException {
		UserEntity user = userRepository.findOne(id);

		if(user == null)
			throw new UserNotFoundException();

		return UserAdapter.entityToDto(user);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDto getUserByUsername(String username) throws UserNotFoundException {
		UserEntity userEntity =
				userRepository.findByFirstname(username)
						.stream()
						.findFirst()
						.orElseThrow(UserNotFoundException::new);
		return UserAdapter.entityToDto(userEntity);
	}

	@Override
	@Transactional
	public UserDto insertUser(UserDto userDto) {
		UserEntity userEntity = UserAdapter.dtoToEntity(userDto);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
		String passwordDto = userEntity.getPassword();
		userEntity.setPassword(passwordEncoder.encode(passwordDto));

		userEntity = userRepository.save(userEntity);

		return UserAdapter.entityToDto(userEntity);
	}
}
