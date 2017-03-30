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
	public void shouldGetOneUserByUsername() {

		String username = "first";

		List<UserEntity> userEntities = userRepository.findByUsername(username);

		assertThat(userEntities).hasSize(1);
		assertThat(userEntities.get(0).getId()).isEqualTo(1);
		assertThat(userEntities.get(0).getFirstname()).isEqualTo("first");
		assertThat(userEntities.get(0).getPassword()).isEqualTo("password1");
	}

	@Test
	public void shouldNotGetUserWithUnknownUsername() {

		String username = "unknown";

		List<UserEntity> userEntities = userRepository.findByUsername(username);

		assertThat(userEntities).hasSize(0);
	}
}
