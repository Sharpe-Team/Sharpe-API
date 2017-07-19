package com.esgi.user;

import com.esgi.role.RoleRepository;
import com.esgi.ruc.RucEntity;
import com.esgi.ruc.RucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final RucRepository rucRepository;

	private final RoleRepository roleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RucRepository rucRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.rucRepository = rucRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public List<UserDto> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(this::getCompleteUserDto)
				.collect(Collectors.toList());
	}

	@Override
	public UserDto getUser(Long id) throws UserNotFoundException {
		UserEntity user = userRepository.findOne(id);

		if(user == null) {
			throw new UserNotFoundException();
		}

		return getCompleteUserDto(user);
	}

	@Override
	public UserDto getUserByUsername(String username) throws UserNotFoundException {
		UserEntity userEntity = userRepository.findByFirstname(username)
				.stream()
				.findFirst()
				.orElseThrow(UserNotFoundException::new);
		return getCompleteUserDto(userEntity);
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

		return getCompleteUserDto(userEntity);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.delete(id);
	}

	@Override
	public UserDto getCompleteUserDto(UserEntity userEntity) {
		UserDto userDto = UserAdapter.entityToDto(userEntity);
		// Hide password
		userDto.setPassword("");

		HashMap<Long, String> roleForCircle = new HashMap<>();

		List<RucEntity> links = rucRepository.findByIdUser(userDto.getId());
		for (RucEntity ruc : links) {
			String roleName = roleRepository.findOne(ruc.getIdRole()).getName();
			roleForCircle.put(ruc.getIdCircle(), roleName);
		}

		userDto.setRuc(roleForCircle);

		return userDto;
	}
}
