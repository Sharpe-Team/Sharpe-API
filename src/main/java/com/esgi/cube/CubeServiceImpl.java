package com.esgi.cube;

import com.esgi.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thomasfouan on 16/06/2017.
 */
@Service
public class CubeServiceImpl implements CubeService {

	private CubeRepository cubeRepository;

	private UserRepository userRepository;

	@Autowired
	public CubeServiceImpl(CubeRepository cubeRepository, UserRepository userRepository) {
		this.cubeRepository = cubeRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<CubeDto> getCubesInLine(Long idLine) {
		return cubeRepository.findByIdLine(idLine)
				.stream()
				.map(this::getCompleteCube)
				.collect(Collectors.toList());
	}

	@Override
	public CubeDto insertCube(CubeDto cubeDto) {
		CubeEntity cubeEntity = CubeAdapter.dtoToEntity(cubeDto);

		cubeEntity = cubeRepository.save(cubeEntity);

		return CubeAdapter.entityToDto(cubeEntity);
	}

	@Override
	public void deleteCube(Long idCube) {
		cubeRepository.delete(idCube);
	}

	private CubeDto getCompleteCube(CubeEntity cubeEntity) {
		CubeDto cubeDto = CubeAdapter.entityToDto(cubeEntity);

		// Get info of the sender of this point
		UserEntity userEntity = userRepository.findOne(cubeDto.getIdUser());
		UserDto userDto;

		if (userEntity != null) {
			userEntity.setPassword("");
			userDto = UserAdapter.entityToDto(userEntity);
		} else {
			userDto = UserDto.builder()
					.firstname("Deleted user")
					.lastname("")
					.email("")
					.build();
		}
		cubeDto.setUser(userDto);
		return cubeDto;
	}
}
