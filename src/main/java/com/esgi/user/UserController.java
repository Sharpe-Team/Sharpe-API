package com.esgi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public List<UserEntity> getAllUsers() {
		return userService.getAllUsers();
	}

	@RequestMapping("/{user_id}")
	public UserEntity getUser(@PathVariable("user_id") Long id) throws UserNotFoundException {
		return userService.getUser(id);
	}

	@RequestMapping(params = "username")
	public UserEntity getUser(@RequestParam("username") String username) throws UserNotFoundException {
		return userService.getUserByUsername(username);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public ResponseEntity insertUser(@RequestBody @Valid UserEntity userEntity,
									 BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			throw new UserValidationException();
		}

		userService.insertUser(userEntity);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{user_id}")
				.buildAndExpand(userEntity.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
}
