package com.esgi.circle;

import com.esgi.user.UserData;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

/**
 * Created by thomasfouan on 29/03/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@CircleData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_CLASS)
public class CircleRepositoryTest {

	@Autowired
	private CircleRepository circleRepository;

	@Test
	public void shoudl_get_all_public_circles() {
		List<CircleEntity> circleEntities = circleRepository.findAllByType((short) 1);

		assertThat(circleEntities).hasSize(2);
		assertThat(circleEntities.get(0).getId()).isEqualTo(1L);
		assertThat(circleEntities.get(1).getId()).isEqualTo(2L);
	}

	@Test
	public void shoudl_get_all_private_circles() {
		List<CircleEntity> circleEntities = circleRepository.findAllByType((short) 2);

		assertThat(circleEntities).hasSize(1);
		assertThat(circleEntities.get(0).getId()).isEqualTo(3L);
	}

	@Test
	public void shoudl_not_get_circles_for_unknown_type() {
		List<CircleEntity> circleEntities = circleRepository.findAllByType((short) 3);

		assertThat(circleEntities).hasSize(0);
	}
}
