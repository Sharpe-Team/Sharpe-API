package com.esgi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@RequestMapping("/{user_id}")
	public UserDto getUser(@PathVariable("user_id") Long id) throws UserNotFoundException {
		return userService.getUser(id);
	}

	@RequestMapping(params = "firstname")
	public UserDto getUser(@RequestParam("firstname") String firstname) throws UserNotFoundException {
		return userService.getUserByUsername(firstname);
	}

	@PostMapping("/subscribe")
	@ResponseStatus(CREATED)
	public UserDto insertUser(@RequestBody @Valid UserDto userDto,
									 BindingResult bindingResult) throws EmailAddressAlreadyExistsException {

		if(bindingResult.hasErrors()) {
			throw new UserValidationException();
		}

		// Check if the email is already used
		try {
			userService.getUserByUsername(userDto.getEmail());
			throw new EmailAddressAlreadyExistsException();
		} catch (UserNotFoundException e) {
			// Hash password before save it in DB
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);

			String password = userDto.getPassword();

			String encodedPwd = passwordEncoder.encode(password);

			userDto.setPassword(encodedPwd);

			userDto = userService.insertUser(userDto);
		}

		/*
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{user_id}")
				.buildAndExpand(userDto.getId())
				.toUri();
				*/

		return userDto;
	}
}
