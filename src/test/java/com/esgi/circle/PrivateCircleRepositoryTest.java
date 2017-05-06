package com.esgi.circle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

/**
 * Created by thomasfouan on 05/05/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@PrivateCircleData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_CLASS)
public class PrivateCircleRepositoryTest {

	@Autowired
	private PrivateCircleRepository privateCircleRepository;

	@Test
	public void should_return_private_circle_with_correct_users() {
		List<PrivateCircleEntity> privateCircleEntities = privateCircleRepository.findByIdUser1AndAndIdUser2(1L, 2L);

		assertThat(privateCircleEntities).hasSize(1);
		assertThat(privateCircleEntities.get(0)).isNotNull();
		assertThat(privateCircleEntities.get(0).getId()).isEqualTo(1L);
	}

	@Test
	public void should_not_return_private_circle_with_correct_users_switched() {
		List<PrivateCircleEntity> privateCircleEntities = privateCircleRepository.findByIdUser1AndAndIdUser2(2L, 1L);

		assertThat(privateCircleEntities).hasSize(0);
	}

	@Test
	public void should_not_return_private_circle_with_incorrect_users() {
		List<PrivateCircleEntity> privateCircleEntities = privateCircleRepository.findByIdUser1AndAndIdUser2(1L, 3L);

		assertThat(privateCircleEntities).hasSize(0);
	}
}
