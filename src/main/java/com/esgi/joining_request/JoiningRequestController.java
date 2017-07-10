package com.esgi.joining_request;

import com.esgi.role.RoleNotFoundException;
import com.esgi.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Created by thomasfouan on 10/07/2017.
 */
@RestController
@RequestMapping("/joining-requests")
@CrossOrigin
public class JoiningRequestController {

	private JoiningRequestService joiningRequestService;

	@Autowired
	public JoiningRequestController(JoiningRequestService joiningRequestService) {
		this.joiningRequestService = joiningRequestService;
	}

	@RequestMapping
	public List<JoiningRequestDto> getAllJoiningRequests() {
		return joiningRequestService.getAllJoiningRequests();
	}

	@RequestMapping(params = "user_id")
	public List<JoiningRequestDto> getJoiningRequestsByUser(@RequestParam("user_id") Long id) {
		return joiningRequestService.getJoiningRequestsByUser(id);
	}

	@RequestMapping(params = "circle_id")
	public List<UserDto> getJoiningRequestsByCircle(@RequestParam("circle_id") Long id) {
		return joiningRequestService.getJoiningRequestsByCircle(id);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public JoiningRequestDto insertLink(@RequestBody @Valid JoiningRequestDto joiningRequestDto, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			throw new JoiningRequestValidationException();
		}

		return joiningRequestService.insertJoiningRequest(joiningRequestDto);
	}

	@DeleteMapping(value = "/{joining_request_id}", params = "accepted")
	@ResponseStatus(NO_CONTENT)
	public void deleteJoiningRequest(@PathVariable("joining_request_id") Long id, @RequestParam("accepted") boolean isAccepted) throws JoiningRequestNotFoundException, RoleNotFoundException {
		joiningRequestService.deleteJoiningRequest(id, isAccepted);
	}
}
