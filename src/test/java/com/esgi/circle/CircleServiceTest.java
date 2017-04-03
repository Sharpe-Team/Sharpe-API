package com.esgi.circle;

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

	private CircleEntity circle1;

	private CircleEntity circle2;

	private CircleEntity circle3;

	private CircleEntity circle4;

	@Before
	public void initCircles() {

		circle1 = CircleEntity.builder()
				.id(1L)
				.name("Cercle 1")
				.pictureUrl("picture1.png")
				.bannerPictureUrl("bannerPicture1.png")
				.build();

		circle2 = CircleEntity.builder()
				.id(2L)
				.name("Cercle 2")
				.pictureUrl("picture2.png")
				.bannerPictureUrl("bannerPicture2.png")
				.build();

		circle3 = CircleEntity.builder()
				.id(3L)
				.name("Cercle 3")
				.pictureUrl("picture3.png")
				.bannerPictureUrl("bannerPicture3.png")
				.build();

		circle4 = CircleEntity.builder()
				.id(4L)
				.name("Cercle 4")
				.pictureUrl("picture4.png")
				.bannerPictureUrl("bannerPicture4.png")
				.build();
	}

	@Before
	public void configureMock() {

		List<CircleEntity> list = new ArrayList<>();
		list.add(circle1);
		list.add(circle2);
		list.add(circle3);

		when(circleRepository.findAll()).thenReturn(list);

		when(circleRepository.findOne(1L)).thenReturn(circle1);

		when(circleRepository.findOne(4L)).thenReturn(null);

		when(circleRepository.save(any(CircleEntity.class))).thenReturn(circle4);
	}

	@Test
	public void shouldGetAllCircles() {

		List<CircleEntity> result = circleService.getAllCircles();

		assertThat(result).hasSize(3);
	}

	@Test
	public void shouldGetOneCircle() {

		Long id = circle1.getId();

		CircleEntity result;

		try {
			result = circleService.getCircle(id);

			assertThat(result).isNotNull();
			assertThat(result.getId()).isEqualTo(circle1.getId());
			assertThat(result.getName()).isEqualTo(circle1.getName());
			assertThat(result.getPictureUrl()).isEqualTo(circle1.getPictureUrl());
			assertThat(result.getBannerPictureUrl()).isEqualTo(circle1.getBannerPictureUrl());
		} catch (CircleNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve one circle with id = " + id);
		}
	}

	@Test
	public void shouldThrowCircleNotFoundExceptionWithUnknownId() {

		Long id = 4L;

		try {
			circleService.getCircle(id);

			fail("Test failed : an exception should have been thrown when trying to retrieve one circle with id = " + id);
		} catch (CircleNotFoundException e) {
		}
	}

	@Test
	public void shouldInsertCircle() {

		CircleEntity circleEntity = CircleEntity.builder()
				.name("new Entity")
				.pictureUrl("new-picture.png")
				.bannerPictureUrl("new-banner-picture.png")
				.build();

		circleEntity = circleService.insertCircle(circleEntity);

		assertThat(circleEntity.getId()).isEqualTo(4L);

		verify(circleRepository, times(1)).save(any(CircleEntity.class));
	}
}
