package com.esgi.line;

import com.esgi.circle.CircleNotFoundException;
import org.assertj.core.api.Java6Assertions;
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
public class LineServiceTest {

	@InjectMocks
	private LineServiceImpl lineService;

	@Mock
	private LineRepository lineRepository;

	private LineEntity line1;

	private LineEntity line2;

	private LineEntity line3;

	private LineEntity line4;

	@Before
	public void init_circles() {

		line1 = LineEntity.builder()
				.id(1L)
				.idCircle(1L)
				.name("line1")
				.announcement("message1")
				.build();

		line2 = LineEntity.builder()
				.id(2L)
				.idCircle(1L)
				.name("line1")
				.announcement("message1")
				.build();

		line3 = LineEntity.builder()
				.id(3L)
				.idCircle(2L)
				.name("line1")
				.announcement("message1")
				.build();

		line4 = LineEntity.builder()
				.id(4L)
				.idCircle(3L)
				.name("line1")
				.announcement("message1")
				.build();
	}

	@Before
	public void configure_mock() {

		List<LineEntity> list = new ArrayList<>();
		list.add(line1);
		list.add(line2);

		when(lineRepository.findByIdCircle(1L)).thenReturn(list);

		when(lineRepository.findOne(3L)).thenReturn(line3);

		when(lineRepository.save(any(LineEntity.class))).thenReturn(line4);
	}

	@Test
	public void should_get_lines_of_circle_1() {

		Long idCircle = 1L;

		try {
			List<LineDto> lines = lineService.getAllLinesInCircle(idCircle);

			assertThat(lines).isNotNull();
			assertThat(lines.size()).isEqualTo(2);
			assertThat(lines.get(0).getId()).isEqualTo(1L);
			assertThat(lines.get(1).getId()).isEqualTo(2L);
			assertThat(lines.get(0).getIdCircle()).isEqualTo(1L);
			assertThat(lines.get(1).getIdCircle()).isEqualTo(1L);
		} catch (CircleNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve lines from circle with id = " + idCircle);
		}
	}

	@Test
	public void should_throw_CircleNotFoundException_for_lines_of_circle_3() {

		Long idCircle = 3L;

		try {
			lineService.getAllLinesInCircle(idCircle);

			fail("Test failed : a CircleNotFoundException should have been thrown when retrieving lines from circle with id = " + idCircle);
		} catch (CircleNotFoundException e) {
		}
	}

	@Test
	public void should_get_one_line() {

		Long id = 3L;

		try {
			LineDto line = lineService.getLine(id);

			assertThat(line).isNotNull();
			assertThat(line.getId()).isEqualTo(line3.getId());
			assertThat(line.getName()).isEqualTo(line3.getName());
			assertThat(line.getAnnouncement()).isEqualTo(line3.getAnnouncement());
		} catch (LineNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve line with id = " + id);
		}
	}

	@Test
	public void should_throw_LineNotFoundException_with_unknown_id() {

		Long id = 4L;

		try {
			lineService.getLine(id);

			fail("Test failed : an exception should have been thrown when trying to retrieve one line with id = " + id);
		} catch (LineNotFoundException e) {
		}
	}

	@Test
	public void should_insert_circle() {

		LineDto lineDto = LineDto.builder()
				.idCircle(3L)
				.name("new Entity")
				.announcement("The Message")
				.build();

		lineDto = lineService.insertLine(lineDto);

		assertThat(lineDto.getId()).isEqualTo(4L);

		verify(lineRepository, times(1)).save(any(LineEntity.class));
	}
}
