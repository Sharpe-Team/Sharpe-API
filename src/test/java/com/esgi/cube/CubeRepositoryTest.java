package com.esgi.cube;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by thomasfouan on 16/06/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@CubeData
@ActiveProfiles("test")
public class CubeRepositoryTest {

	@Autowired
	CubeRepository cubeRepository;

	@Test
	public void should_get_all_cubes_by_id() {
		Long idLine = 1L;

		List<CubeEntity> cubes = cubeRepository.findByIdLine(idLine);

		assertThat(cubes).hasSize(2);
		assertThat(cubes.get(0).getIdLine()).isEqualTo(idLine);
	}

	@Test
	public void should_not_get_cubes_for_unknown_line() {
		Long idLine = 4L;

		List<CubeEntity> cubes = cubeRepository.findByIdLine(idLine);

		assertThat(cubes).hasSize(0);
	}
}
