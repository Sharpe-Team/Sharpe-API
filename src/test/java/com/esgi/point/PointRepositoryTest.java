package com.esgi.point;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by rfruitet on 07/03/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@MessageData
//@AutoConfigureTestDatabase(connection = H2)
@ActiveProfiles("test")
public class PointRepositoryTest {

	@Autowired
	private PointRepository pointRepository;

	@Test
	public void should_return_last_tree_point_of_topic_one() {
		List<PointEntity> pointEntities = pointRepository.findByCercle(1L);

		System.out.println(pointEntities.toString());

		assertThat(pointEntities).hasSize(3);
		assertThat(pointEntities.get(0).getId()).isEqualTo(1);
		assertThat(pointEntities.get(0).getUsername()).isEqualTo("first");
		assertThat(pointEntities.get(0).getPoint()).isEqualTo("message1");
	}

	@Test
	public void should_insert_new_point() {
		PointEntity pointEntity = PointEntity.builder().id(11L)
				.cercle(6L)
				.username("eleventh")
				.point("message11")
				.build();

		PointEntity entity = pointRepository.save(pointEntity);

		assertThat(entity.getId()).isEqualTo(11);
		assertThat(entity.getCercle()).isEqualTo(6);
		assertThat(entity.getUsername()).isEqualTo("eleventh");
		assertThat(entity.getPoint()).isEqualTo("message11");
	}

	@Test
	public void should_delete_the_second_point() {
		pointRepository.delete(2L);

		List<PointEntity> pointEntities = pointRepository.findByCercle(1L);

		assertThat(pointEntities).hasSize(2);
		assertThat(pointEntities.get(1).getId()).isEqualTo(3);
		assertThat(pointEntities.get(1).getUsername()).isEqualTo("third");
		assertThat(pointEntities.get(1).getPoint()).isEqualTo("message3");
	}
}
