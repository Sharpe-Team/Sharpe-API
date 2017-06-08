package com.esgi.role;

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
@RequestMapping("/roles")
@CrossOrigin
public class RoleController {

	private RoleService roleService;

	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@RequestMapping
	public List<RoleDto> getAllRoles() {
		return roleService.getAllRoles();
	}

	@RequestMapping("/{role_id}")
	public RoleDto getRole(@PathVariable("role_id") Long id) throws RoleNotFoundException {
		return roleService.getRole(id);
	}

	@RequestMapping(params = "name")
	public RoleDto getRole(@RequestParam("name") String name) throws RoleNotFoundException {
		return roleService.getRoleByName(name);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public RoleDto insertRole(@RequestBody @Valid RoleDto roleDto, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			throw new RoleValidationException();
		}

		return roleService.insertRole(roleDto);
	}
}
