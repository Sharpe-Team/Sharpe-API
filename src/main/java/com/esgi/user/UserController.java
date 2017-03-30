package com.esgi.user;

import org.springframework.beans.factory.annotation.Autowired;
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

	@PostMapping
	@ResponseStatus(CREATED)
	public ResponseEntity insertUser(@RequestBody @Valid UserDto userDto,
									 BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			throw new UserValidationException();
		}

		userService.insertUser(userDto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{user_id}")
				.buildAndExpand(userDto.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
}
