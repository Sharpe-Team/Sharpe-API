package com.esgi.joining_request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by thomasfouan on 10/07/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@JoiningRequestData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class JoiningRequestRepositoryTest {

	@Autowired
	private JoiningRequestRepository joiningRequestRepository;

	@Test
	public void should_get_all_joining_requests_by_idUser() {
		Long id = 1L;

		List<JoiningRequestEntity> joiningRequests = joiningRequestRepository.findByIdUser(id);

		assertThat(joiningRequests).isNotNull();
		assertThat(joiningRequests).hasSize(2);
	}

	@Test
	public void should_not_get_joining_request_for_unknown_idUser() {
		Long id = 3L;

		List<JoiningRequestEntity> joiningRequests = joiningRequestRepository.findByIdUser(id);

		assertThat(joiningRequests).isNotNull();
		assertThat(joiningRequests).hasSize(0);
	}

	@Test
	public void should_get_all_joining_requests_by_idCircle() {
		Long id = 1L;

		List<JoiningRequestEntity> joiningRequests = joiningRequestRepository.findByIdCircle(id);

		assertThat(joiningRequests).isNotNull();
		assertThat(joiningRequests).hasSize(2);
	}

	@Test
	public void should_not_get_joining_request_for_unknown_idCircle() {
		Long id = 3L;

		List<JoiningRequestEntity> joiningRequests = joiningRequestRepository.findByIdCircle(id);

		assertThat(joiningRequests).isNotNull();
		assertThat(joiningRequests).hasSize(0);
	}
}
