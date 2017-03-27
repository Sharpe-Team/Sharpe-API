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
@PointData
//@AutoConfigureTestDatabase(connection = H2)
@ActiveProfiles("test")
public class PointRepositoryTest {

	@Autowired
	private PointRepository pointRepository;

	@Test
	public void should_return_last_tree_point_of_topic_one() {
		List<PointEntity> pointEntities = pointRepository.findByIdLine(1L);

		System.out.println(pointEntities.toString());

		assertThat(pointEntities).hasSize(3);
		assertThat(pointEntities.get(0).getId()).isEqualTo(1);
		assertThat(pointEntities.get(0).getIdUser()).isEqualTo(1L);
		assertThat(pointEntities.get(0).getContent()).isEqualTo("message1");
	}

	@Test
	public void should_insert_new_point() {
		PointEntity pointEntity = PointEntity.builder().id(11L)
				.idLine(6L)
				.idUser(8L)
				.content("message11")
				.build();

		PointEntity entity = pointRepository.save(pointEntity);

		assertThat(entity.getId()).isEqualTo(11);
		assertThat(entity.getIdLine()).isEqualTo(6);
		assertThat(entity.getIdUser()).isEqualTo(8L);
		assertThat(entity.getContent()).isEqualTo("message11");
	}

	@Test
	public void should_delete_the_second_point() {
		pointRepository.delete(2L);

		List<PointEntity> pointEntities = pointRepository.findByIdLine(1L);

		assertThat(pointEntities).hasSize(2);
		assertThat(pointEntities.get(1).getId()).isEqualTo(3);
		assertThat(pointEntities.get(1).getIdUser()).isEqualTo(3L);
		assertThat(pointEntities.get(1).getContent()).isEqualTo("message3");
	}
}
