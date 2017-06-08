package com.esgi.role;

import org.springframework.stereotype.Component;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@Component
public class RoleAdapter {

	public static RoleEntity dtoToEntity(RoleDto roleDto) {
		return RoleEntity.builder()
				.id(roleDto.getId())
				.name(roleDto.getName())
				.build();
	}

	public static RoleDto entityToDto(RoleEntity roleEntity) {
		return RoleDto.builder()
				.id(roleEntity.getId())
				.name(roleEntity.getName())
				.build();
	}
}
