package com.esgi.role;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 08/06/2017.
 */
public interface RoleService {

	@Transactional(readOnly = true)
	List<RoleDto> getAllRoles();

	@Transactional(readOnly = true)
	RoleDto getRole(Long id) throws RoleNotFoundException;

	@Transactional(readOnly = true)
	RoleDto getRoleByName(String name) throws RoleNotFoundException;

	@Transactional
	RoleDto insertRole(RoleDto roleDto);
}
