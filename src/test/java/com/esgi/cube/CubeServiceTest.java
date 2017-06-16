package com.esgi.cube;

import com.esgi.user.UserEntity;
import com.esgi.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by thomasfouan on 16/06/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CubeServiceTest {

	@InjectMocks
	private CubeServiceImpl cubeService;

	@Mock
	private CubeRepository cubeRepository;

	@Mock
	private UserRepository userRepository;

	private CubeEntity cube1;
	private CubeEntity cube2;
	private CubeEntity cube3;

	private UserEntity user1;
	private UserEntity user2;

	public void init_cubes() {
		cube1 = CubeEntity.builder()
				.id(1L)
				.idLine(1L)
				.idUser(1L)
				.url("./uploads/image1.jpg")
				.build();

		cube2 = CubeEntity.builder()
				.id(2L)
				.idLine(1L)
				.idUser(2L)
				.url("./uploads/image2.jpg")
				.build();

		cube3 = CubeEntity.builder()
				.id(3L)
				.idLine(2L)
				.idUser(1L)
				.url("./uploads/image3.jpg")
				.build();

		user1 = UserEntity.builder()
				.id(1L)
				.firstname("firstname1")
				.lastname("lastname1")
				.email("email1@test.fr")
				.build();

		user2 = UserEntity.builder()
				.id(2L)
				.firstname("firstname2")
				.lastname("lastname2")
				.email("email2@test.fr")
				.build();
	}

	@Before
	public void configure_mock() {
		init_cubes();

		List<CubeEntity> list = new ArrayList<>();
		list.add(cube1);
		list.add(cube2);

		when(cubeRepository.findByIdLine(1L)).thenReturn(list);
		when(cubeRepository.save(any(CubeEntity.class))).thenReturn(cube3);

		when(userRepository.findOne(1L)).thenReturn(user1);
		when(userRepository.findOne(2L)).thenReturn(user2);
	}

	@Test
	public void should_get_cubes_of_line_1() {
		long idLine = 1L;

		List<CubeDto> cubes = cubeService.getCubesInLine(idLine);

		assertThat(cubes).isNotNull();
		assertThat(cubes).hasSize(2);
		assertThat(cubes.get(0).getId()).isEqualTo(cube1.getId());
		assertThat(cubes.get(0).getIdLine()).isEqualTo(idLine);
		assertThat(cubes.get(0).getUser()).isNotNull();
		assertThat(cubes.get(0).getUser().getId()).isEqualTo(cube1.getIdUser());
		assertThat(cubes.get(1).getId()).isEqualTo(cube2.getId());
	}

	@Test
	public void should_not_get_cube_for_unknown_line() {
		long idLine = 3L;

		List<CubeDto> cubes = cubeService.getCubesInLine(idLine);

		assertThat(cubes).isNotNull();
		assertThat(cubes).hasSize(0);
	}

	@Test
	public void should_insert_cube() {
		CubeDto cubeDto = CubeAdapter.entityToDto(cube3);
		cubeDto.setId(null);

		CubeDto insertedCube = cubeService.insertCube(cubeDto);

		assertThat(insertedCube).isNotNull();
		assertThat(insertedCube.getId()).isEqualTo(cube3.getId());
		assertThat(insertedCube.getIdLine()).isEqualTo(cube3.getIdLine());

		verify(cubeRepository, times(1)).save(any(CubeEntity.class));
	}

	@Test
	public void should_delete_cube() {
		cubeService.deleteCube(cube3.getId());

		verify(cubeRepository, times(1)).delete(cube3.getId());
	}
}
