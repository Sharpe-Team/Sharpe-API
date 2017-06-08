package com.esgi.ruc;

/**
 * Created by thomasfouan on 08/06/2017.
 */
public class RucAdapter {

	public static RucEntity dtoToEntity(RucDto rucDto) {
		return RucEntity.builder()
				.id(rucDto.getId())
				.idRole(rucDto.getIdRole())
				.idUser(rucDto.getIdUser())
				.idCircle(rucDto.getIdCircle())
				.build();
	}

	public static RucDto entityToDto(RucEntity rucEntity) {
		return RucDto.builder()
				.id(rucEntity.getId())
				.idRole(rucEntity.getIdRole())
				.idUser(rucEntity.getIdUser())
				.idCircle(rucEntity.getIdCircle())
				.build();
	}
}
