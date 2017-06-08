package com.esgi.role;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

	@InjectMocks
	private RoleServiceImpl roleService;

	@Mock
	private RoleRepository roleRepository;

	private RoleEntity roleEntity1;
	private RoleEntity roleEntity2;
	private RoleEntity roleEntity3;

	public void initRoles() {
		roleEntity1 = RoleEntity.builder()
				.id(1L)
				.name("USER")
				.build();

		roleEntity2 = RoleEntity.builder()
				.id(2L)
				.name("MODERATOR")
				.build();

		roleEntity3 = RoleEntity.builder()
				.id(3L)
				.name("NEW")
				.build();
	}

	@Before
	public void configureMock() {

		List<RoleEntity> list = new ArrayList<>();
		list.add(roleEntity1);
		list.add(roleEntity2);

		List<RoleEntity> list2 = new ArrayList<>();
		list2.add(roleEntity2);

		when(roleRepository.findAll()).thenReturn(list);
		when(roleRepository.findOne(1L)).thenReturn(roleEntity1);
		when(roleRepository.findOne(4L)).thenReturn(null);
		when(roleRepository.findByName(roleEntity2.getName())).thenReturn(list2);
		when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity3);
	}

	@Test
	public void should_get_all_roles() {
		List<RoleDto> roles = roleService.getAllRoles();

		assertThat(roles).hasSize(2);
	}

	@Test
	public void should_get_role_by_id() {
		Long id = roleEntity1.getId();

		try {
			RoleDto roleDto = roleService.getRole(id);

			assertThat(roleDto).isNotNull();
			assertThat(roleDto.getId()).isEqualTo(id);
			assertThat(roleDto.getName()).isEqualTo(roleEntity1.getName());
		} catch (RoleNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve the role with id = " + id);
		}
	}

	@Test
	public void should_throw_RoleNotFoundException_with_unknown_id() {
		Long id = 4L;

		try {
			roleService.getRole(id);

			fail("Test failed : an exception should have been thrown when trying to retrieve an unknown role with id = " + id);
		} catch (RoleNotFoundException e) {
		}
	}

	@Test
	public void should_get_one_role_by_name() {
		String name = roleEntity2.getName();

		try {
			RoleDto roleDto = roleService.getRoleByName(name);

			assertThat(roleDto).isNotNull();
			assertThat(roleDto.getId()).isEqualTo(roleEntity2.getId());
			assertThat(roleDto.getName()).isEqualTo(roleEntity2.getName());
		} catch (RoleNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve the role with name = " + name);
		}
	}

	@Test
	public void should_throw_RoleNotFoundException_with_unknown_name() {
		String name = "UNKNOWN";

		try {
			roleService.getRoleByName(name);

			fail("Test failed : an exception should have been thrown when trying to retrieve an unknown role with name = " + name);
		} catch (RoleNotFoundException e) {
		}
	}

	@Test
	public void should_insert_role() {
		RoleDto roleDto = RoleDto.builder()
				.name(roleEntity3.getName())
				.build();

		roleDto = roleService.insertRole(roleDto);

		assertThat(roleDto).isNotNull();
		assertThat(roleDto.getId()).isEqualTo(roleEntity3.getId());
		assertThat(roleDto.getName()).isEqualTo(roleEntity3.getName());

		verify(roleRepository, times(1)).save(any(RoleEntity.class));
	}

	@Test
	public void should_not_insert_role_without_name() {
		RoleDto roleDto = RoleDto.builder()
				.build();

		roleDto = roleService.insertRole(roleDto);
	}
}
