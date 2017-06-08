package com.esgi.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<RoleDto> getAllRoles() {
		return roleRepository.findAll()
				.stream()
				.map(RoleAdapter::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public RoleDto getRole(Long id) throws RoleNotFoundException {
		RoleEntity roleEntity = roleRepository.findOne(id);

		if(roleEntity == null) {
			throw new RoleNotFoundException();
		}

		return RoleAdapter.entityToDto(roleEntity);
	}

	@Override
	public RoleDto getRoleByName(String name) throws RoleNotFoundException {
		RoleEntity roleEntity = roleRepository.findByName(name)
				.stream()
				.findFirst()
				.orElseThrow(RoleNotFoundException::new);

		return RoleAdapter.entityToDto(roleEntity);
	}

	@Override
	public RoleDto insertRole(RoleDto roleDto) {
		RoleEntity roleEntity = RoleAdapter.dtoToEntity(roleDto);

		roleEntity = roleRepository.save(roleEntity);

		return RoleAdapter.entityToDto(roleEntity);
	}
}
