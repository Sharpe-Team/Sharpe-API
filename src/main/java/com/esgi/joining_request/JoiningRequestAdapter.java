package com.esgi.joining_request;

/**
 * Created by thomasfouan on 10/07/2017.
 */
public class JoiningRequestAdapter {

	public static JoiningRequestEntity dtoToEntity(JoiningRequestDto dto) {
		return JoiningRequestEntity.builder()
				.id(dto.getId())
				.idUser(dto.getIdUser())
				.idCircle(dto.getIdCircle())
				.build();
	}

	public static JoiningRequestDto entityToDto(JoiningRequestEntity entity) {
		return JoiningRequestDto.builder()
				.id(entity.getId())
				.idUser(entity.getIdUser())
				.idCircle(entity.getIdCircle())
				.build();
	}
}
