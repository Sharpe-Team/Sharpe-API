package com.esgi.circle;

import com.esgi.line.LineEntity;
import com.esgi.line.LineRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by thomasfouan on 29/03/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CircleServiceTest {

	@InjectMocks
	private CircleServiceImpl circleService;

	@Mock
	private CircleRepository circleRepository;

	@Mock
	private LineRepository lineRepository;

	@Mock
	private PrivateCircleRepository privateCircleRepository;

	private CircleEntity circle1;
	private CircleEntity circle2;
	private CircleEntity circle3;
	private CircleEntity circle4;
	private CircleEntity circle5;

	private LineEntity line;

	private PrivateCircleEntity privateCircle;

	public void init_circles() {

		circle1 = CircleEntity.builder()
				.id(1L)
				.name("Cercle 1")
				.pictureUrl("picture1.png")
				.bannerPictureUrl("bannerPicture1.png")
				.type((short) 1)
				.build();

		circle2 = CircleEntity.builder()
				.id(2L)
				.name("Cercle 2")
				.pictureUrl("picture2.png")
				.bannerPictureUrl("bannerPicture2.png")
				.type((short) 1)
				.build();

		circle3 = CircleEntity.builder()
				.id(3L)
				.name("Cercle 3")
				.pictureUrl("picture3.png")
				.bannerPictureUrl("bannerPicture3.png")
				.type((short) 1)
				.build();

		circle4 = CircleEntity.builder()
				.id(4L)
				.name("Cercle 4")
				.pictureUrl("picture4.png")
				.bannerPictureUrl("bannerPicture4.png")
				.type((short) 1)
				.build();

		circle5 = CircleEntity.builder()
				.id(5L)
				.name("Cercle privé")
				.type((short) 2)
				.build();

		line = LineEntity.builder()
				.id(1L)
				.idCircle(1L)
				.name("Line 1")
				.announcement("")
				.build();

		privateCircle = PrivateCircleEntity.builder()
				.id(1L)
				.idCircle(1L)
				.idUser1(1L)
				.idUser2(2L)
				.build();
	}

	@Before
	public void configure_mock() {
		init_circles();

		List<CircleEntity> list = new ArrayList<>();
		list.add(circle1);
		list.add(circle2);
		list.add(circle3);

		List<LineEntity> listLine = new ArrayList<>();
		listLine.add(line);

		List<PrivateCircleEntity> listPrivateCircle = new ArrayList<>();
		listPrivateCircle.add(privateCircle);

		when(circleRepository.findAll()).thenReturn(list);
		when(circleRepository.findOne(1L)).thenReturn(circle1);
		when(circleRepository.findOne(4L)).thenReturn(null);
		when(circleRepository.save(any(CircleEntity.class))).thenReturn(circle4);
		when(circleRepository.findAllByType((short) 1)).thenReturn(list);

		when(lineRepository.findByIdCircle(1L)).thenReturn(listLine);
		when(lineRepository.save(any(LineEntity.class))).thenReturn(line);

		when(privateCircleRepository.findByIdUser1AndAndIdUser2(1L, 2L)).thenReturn(listPrivateCircle);
		when(privateCircleRepository.save(any(PrivateCircleEntity.class))).thenReturn(privateCircle);
	}

	@Test
	public void should_get_all_circles() {

		List<CircleDto> result = circleService.getAllCircles();

		assertThat(result).hasSize(3);
	}

	@Test
	public void should_get_one_circle() {

		Long id = circle1.getId();

		CircleDto result;

		try {
			result = circleService.getCircle(id);

			assertThat(result).isNotNull();
			assertThat(result.getId()).isEqualTo(circle1.getId());
			assertThat(result.getName()).isEqualTo(circle1.getName());
			assertThat(result.getPictureUrl()).isEqualTo(circle1.getPictureUrl());
			assertThat(result.getBannerPictureUrl()).isEqualTo(circle1.getBannerPictureUrl());
			assertThat(result.getLines()).hasSize(1);
		} catch (CircleNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve one circle with id = " + id);
		}
	}

	@Test
	public void should_throw_CircleNotFoundException_with_unknown_id() {

		Long id = 4L;

		try {
			circleService.getCircle(id);

			fail("Test failed : an exception should have been thrown when trying to retrieve one circle with id = " + id);
		} catch (CircleNotFoundException e) {
		}
	}

	@Test
	public void should_insert_circle() {

		CircleEntity circleEntity = CircleEntity.builder()
				.name("new Entity")
				.pictureUrl("new-picture.png")
				.bannerPictureUrl("new-banner-picture.png")
				.build();

		CircleDto circleDto = circleService.insertCircle(circleEntity);

		assertThat(circleDto.getId()).isEqualTo(4L);
		assertThat(circleDto.getLines()).hasSize(1);
		assertThat(circleDto.getLines().get(0).getId()).isEqualTo(line.getId());

		verify(circleRepository, times(1)).save(any(CircleEntity.class));
	}

	@Test
	public void should_get_existing_private_circle() {

		try {
			CircleDto circle = circleService.getPrivateCircle(1L, 2L);

			assertThat(circle).isNotNull();
			assertThat(circle.getId()).isEqualTo(circle1.getId());
			assertThat(circle.getName()).isEqualTo(circle1.getName());

			verify(privateCircleRepository, times(1)).findByIdUser1AndAndIdUser2(1L, 2L);
		} catch (CircleNotFoundException e) {
			fail("An unexpected exception has been thrown");
		}
	}

	@Test
	public void should_get_existing_private_circle_with_ids_switched() {

		try {
			CircleDto circle = circleService.getPrivateCircle(2L, 1L);

			assertThat(circle).isNotNull();
			assertThat(circle.getId()).isEqualTo(circle1.getId());
			assertThat(circle.getName()).isEqualTo(circle1.getName());

			verify(privateCircleRepository, times(1)).findByIdUser1AndAndIdUser2(1L, 2L);
			verify(privateCircleRepository, times(1)).findByIdUser1AndAndIdUser2(2L, 1L);
		} catch (CircleNotFoundException e) {
			fail("An unexpected exception has been thrown");
		}
	}

	@Test
	public void should_create_new_private_circle() {

		when(circleRepository.save(any(CircleEntity.class))).thenReturn(circle5);

		try {
			CircleDto circle = circleService.getPrivateCircle(2L, 3L);

			assertThat(circle).isNotNull();
			assertThat(circle.getId()).isEqualTo(circle5.getId());
			assertThat(circle.getLines()).hasSize(1);
			assertThat(circle.getType()).isNotNull();
			assertThat(circle.getType()).isEqualTo((short) 2);

			verify(privateCircleRepository, times(1)).findByIdUser1AndAndIdUser2(2L, 3L);
			verify(privateCircleRepository, times(1)).findByIdUser1AndAndIdUser2(3L, 2L);
			verify(privateCircleRepository, times(1)).save(any(PrivateCircleEntity.class));

			verify(circleRepository, times(1)).save(any(CircleEntity.class));
		} catch (CircleNotFoundException e) {
			fail("An unexpected exception has been thrown");
		}
	}

	@Test
	public void should_get_all_public_circles() {
		List<CircleDto> publicCircles = circleService.getAllPublicCircles();

		assertThat(publicCircles).hasSize(3);
		assertThat(publicCircles.get(0)).isNotNull();
		assertThat(publicCircles.get(0).getType()).isEqualTo((short) 1);
		assertThat(publicCircles.get(1)).isNotNull();
		assertThat(publicCircles.get(1).getType()).isEqualTo((short) 1);
		assertThat(publicCircles.get(2)).isNotNull();
		assertThat(publicCircles.get(2).getType()).isEqualTo((short) 1);
	}
}
