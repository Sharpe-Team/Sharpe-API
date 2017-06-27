package com.esgi.ruc;

import com.esgi.role.RoleNotFoundException;
import com.esgi.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@RestController
@RequestMapping("/rucs")
@CrossOrigin
public class RucController {

	private RucService rucService;

	@Autowired
	public RucController(RucService rucService) {
		this.rucService = rucService;
	}

	@RequestMapping
	public List<RucDto> getAllLinks() {
		return rucService.getAllLinks();
	}

	@RequestMapping(params = "role_id")
	public List<RucDto> getLinksByRole(@RequestParam("role_id") Long id) {
		return rucService.getLinksByRole(id);
	}

	@RequestMapping(params = "user_id")
	public List<RucDto> getLinksByUser(@RequestParam("user_id") Long id) {
		return rucService.getLinksByUser(id);
	}

	@RequestMapping(params = "circle_id")
	public List<RucDto> getLinksByCircle(@RequestParam("circle_id") Long id) {
		return rucService.getLinksByCircle(id);
	}

	@RequestMapping(params = {"role_id", "user_id"})
	public List<RucDto> getLinksByRoleAndUser(@RequestParam("role_id") Long idRole, @RequestParam("user_id") Long idUser) {
		return rucService.getLinksByRoleAndUser(idRole, idUser);
	}

	@RequestMapping(params = {"role_id", "circle_id"})
	public List<UserDto> getLinksByRoleAndCircle(@RequestParam("role_id") Long idRole, @RequestParam("circle_id") Long idCircle) {
		return rucService.getLinksByRoleAndCircle(idRole, idCircle);
	}

	@RequestMapping(params = {"user_id", "circle_id"})
	public RucDto getLinkByUserAndCircle(@RequestParam("user_id") Long idUser, @RequestParam("circle_id") Long idCircle) throws RucNotFoundException {
		return rucService.getLinkByUserAndCircle(idUser, idCircle);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public RucDto insertLink(@RequestBody @Valid RucDto rucDto, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			throw new RucValidationException();
		}

		return rucService.insertLink(rucDto);
	}

	@DeleteMapping(params = {"user_id", "circle_id"})
	public List<RucDto> deleteLink(@RequestParam("user_id") Long idUser, @RequestParam("circle_id") Long idCircle) {
		return rucService.deleteLink(idUser, idCircle);
	}

	@PutMapping(params = {"user_id", "circle_id", "role_id"})
	public RucDto updateRoleWithId(@RequestParam("user_id") Long idUser, @RequestParam("circle_id") Long idCircle, @RequestParam("role_id") Long idRole) {
		return rucService.updateRole(idUser, idCircle, idRole);
	}

	@PutMapping(params = {"user_id", "circle_id", "role_name"})
	public RucDto updateRoleWithName(@RequestParam("user_id") Long idUser, @RequestParam("circle_id") Long idCircle, @RequestParam("role_name") String roleName) throws RoleNotFoundException {
		return rucService.updateRole(idUser, idCircle, roleName);
	}
}
