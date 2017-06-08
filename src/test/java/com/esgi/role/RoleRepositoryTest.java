package com.esgi.role;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@RoleData
@ActiveProfiles("test")
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void should_get_one_role_by_name() {

		String name = "USER";

		List<RoleEntity> roles = roleRepository.findByName(name);

		assertThat(roles).hasSize(1);
		assertThat(roles.get(0).getName()).isEqualTo(name);
	}

	@Test
	public void should_not_get_role_for_unknown_role_name() {

		String name = "BLABLA";

		List<RoleEntity> roles = roleRepository.findByName(name);

		assertThat(roles).hasSize(0);
	}
}
