package com.esgi.line;

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
@LineData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_CLASS)
public class LineRepositoryTest {

	@Autowired
	private LineRepository repository;

	@Test
	public void shouldGet2LinesForIdCircle1() {

		List<LineEntity> list = repository.findByIdCircle(1L);

		assertThat(list).hasSize(2);
		assertThat(list.get(0).getId()).isEqualTo(1L);
		assertThat(list.get(1).getId()).isEqualTo(2L);
	}

	@Test
	public void shouldGet1LineForIdCircle3() {

		List<LineEntity> list = repository.findByIdCircle(2L);

		assertThat(list).hasSize(1);
		assertThat(list.get(0).getId()).isEqualTo(3L);
	}

	@Test
	public void shouldGetOLineForIdCircle4() {

		List<LineEntity> list = repository.findByIdCircle(3L);

		assertThat(list).hasSize(0);
	}
}
