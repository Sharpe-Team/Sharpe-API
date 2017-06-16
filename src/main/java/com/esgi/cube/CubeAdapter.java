package com.esgi.cube;

/**
 * Created by thomasfouan on 16/06/2017.
 */
public class CubeAdapter {

	public static CubeEntity dtoToEntity(CubeDto cubeDto) {
		return CubeEntity.builder()
				.id(cubeDto.getId())
				.idLine(cubeDto.getIdLine())
				.idUser(cubeDto.getIdUser())
				.url(cubeDto.getUrl())
				.created(cubeDto.getCreated())
				.updated(cubeDto.getUpdated())
				.build();
	}

	public static CubeDto entityToDto(CubeEntity cubeEntity) {
		return CubeDto.builder()
				.id(cubeEntity.getId())
				.idLine(cubeEntity.getIdLine())
				.idUser(cubeEntity.getIdUser())
				.url(cubeEntity.getUrl())
				.created(cubeEntity.getCreated())
				.updated(cubeEntity.getUpdated())
				.build();
	}
}
