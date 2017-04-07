package com.esgi.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@UserData
//@AutoConfigureTestDatabase(connection = H2)
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void should_get_one_user_by_firstname() {

		String username = "first";

		List<UserEntity> userEntities = userRepository.findByFirstname(username);

		assertThat(userEntities).hasSize(1);
		assertThat(userEntities.get(0).getId()).isEqualTo(1);
		assertThat(userEntities.get(0).getFirstname()).isEqualTo("first");
		assertThat(userEntities.get(0).getPassword()).isEqualTo("password1");
	}

	@Test
	public void should_not_get_user_with_unknown_firstname() {

		String username = "unknown";

		List<UserEntity> userEntities = userRepository.findByFirstname(username);

		assertThat(userEntities).hasSize(0);
	}

	@Test
	public void should_not_get_user_with_unknown_email() {

		String email = "one@email.com";

		UserEntity userEntity = userRepository.findByEmail(email);

		assertThat(userEntity.getEmail()).isEqualTo(email);
	}
}
