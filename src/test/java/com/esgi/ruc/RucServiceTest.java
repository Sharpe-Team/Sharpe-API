package com.esgi.ruc;

import com.esgi.role.RoleEntity;
import com.esgi.role.RoleNotFoundException;
import com.esgi.role.RoleRepository;
import com.esgi.user.UserDto;
import com.esgi.user.UserEntity;
import com.esgi.user.UserRepository;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RucServiceTest {

	@InjectMocks
	private RucServiceImpl rucService;

	@Mock
	private RucRepository rucRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private UserRepository userRepository;

	private RucEntity rucEntity1;
	private RucEntity rucEntity2;
	private RucEntity rucEntity3;
	private RucEntity rucEntity4;
	private RucEntity rucEntity5;
	private RucEntity rucEntity6;
	private RucEntity rucEntity7;

	private RoleEntity roleEntity;

	private UserEntity userEntity;

	public void initRucs() {
		rucEntity1 = RucEntity.builder()
				.id(1L)
				.idRole(1L)
				.idUser(1L)
				.idCircle(1L)
				.build();

		rucEntity2 = RucEntity.builder()
				.id(2L)
				.idRole(2L)
				.idUser(1L)
				.idCircle(2L)
				.build();

		rucEntity3 = RucEntity.builder()
				.id(3L)
				.idRole(2L)
				.idUser(2L)
				.idCircle(1L)
				.build();

		rucEntity4 = RucEntity.builder()
				.id(4L)
				.idRole(1L)
				.idUser(2L)
				.idCircle(2L)
				.build();

		rucEntity5 = RucEntity.builder()
				.id(5L)
				.idRole(2L)
				.idUser(2L)
				.idCircle(2L)
				.build();

		rucEntity6 = RucEntity.builder()
				.id(4L)
				.idRole(2L)
				.idUser(2L)
				.idCircle(2L)
				.build();

		rucEntity7 = RucEntity.builder()
				.id(7L)
				.idUser(3L)
				.idCircle(3L)
				.idRole(2L)
				.build();

		roleEntity = RoleEntity.builder()
				.id(2L)
				.name("MODERATOR")
				.build();

		userEntity = UserEntity.builder()
				.id(1L)
				.firstname("firstname")
				.lastname("lastname")
				.email("toto@esgi.fr")
				.build();
	}

	@Before
	public void configureMock() {
		initRucs();

		List<RucEntity> list = new ArrayList<>();
		list.add(rucEntity1);
		list.add(rucEntity2);
		list.add(rucEntity3);
		list.add(rucEntity4);

		List<RucEntity> listRole = new ArrayList<>();
		listRole.add(rucEntity1);
		listRole.add(rucEntity4);

		List<RucEntity> listUser = new ArrayList<>();
		listUser.add(rucEntity1);
		listUser.add(rucEntity2);

		List<RucEntity> listCircle = new ArrayList<>();
		listCircle.add(rucEntity1);
		listCircle.add(rucEntity3);

		List<RucEntity> listRoleUser = new ArrayList<>();
		listRoleUser.add(rucEntity3);

		List<RucEntity> listRoleCircle = new ArrayList<>();
		listRoleCircle.add(rucEntity2);

		List<RucEntity> listUserCircle = new ArrayList<>();
		listUserCircle.add(rucEntity4);

		when(rucRepository.findAll()).thenReturn(list);
		when(rucRepository.findByIdRole(1L)).thenReturn(listRole);
		when(rucRepository.findByIdUser(1L)).thenReturn(listUser);
		when(rucRepository.findByIdCircle(1L)).thenReturn(listCircle);
		when(rucRepository.findByIdRoleAndIdUser(2L, 2L)).thenReturn(listRoleUser);
		when(rucRepository.findByIdRoleAndIdCircle(2L, 2L)).thenReturn(listRoleCircle);
		when(rucRepository.findByIdUserAndIdCircle(2L, 2L)).thenReturn(listUserCircle);
		when(rucRepository.findByIdUserAndIdCircle(3L, 3L)).thenReturn(new ArrayList<>());

		List<RoleEntity> listRoleEntity = new ArrayList<>();
		listRoleEntity.add(roleEntity);

		when(roleRepository.findByName(roleEntity.getName())).thenReturn(listRoleEntity);
		when(roleRepository.findByName("UNKNOWN")).thenReturn(new ArrayList<>());

		when(userRepository.findOne(1L)).thenReturn(userEntity);
	}

	@Test
	public void should_get_all_links() {
		final List<RucDto> links = rucService.getAllLinks();

		assertThat(links).hasSize(4);
	}

	@Test
	public void should_get_links_by_role() {
		Long id = 1L;
		final List<RucDto> links = rucService.getLinksByRole(id);

		assertThat(links).hasSize(2);
		assertThat(links.get(0).getId()).isEqualTo(rucEntity1.getId());
		assertThat(links.get(1).getId()).isEqualTo(rucEntity4.getId());
	}

	@Test
	public void should_get_links_by_user() {
		Long id = 1L;
		final List<RucDto> links = rucService.getLinksByUser(id);

		assertThat(links).hasSize(2);
		assertThat(links.get(0).getId()).isEqualTo(rucEntity1.getId());
		assertThat(links.get(1).getId()).isEqualTo(rucEntity2.getId());
	}

	@Test
	public void should_get_links_by_circle() {
		Long id = 1L;
		final List<RucDto> links = rucService.getLinksByCircle(id);

		assertThat(links).hasSize(2);
		assertThat(links.get(0).getId()).isEqualTo(rucEntity1.getId());
		assertThat(links.get(1).getId()).isEqualTo(rucEntity3.getId());
	}

	@Test
	public void should_get_links_by_role_and_user() {
		Long idRole = 2L;
		Long idUser = 2L;
		final List<RucDto> links = rucService.getLinksByRoleAndUser(idRole, idUser);

		assertThat(links).hasSize(1);
		assertThat(links.get(0).getId()).isEqualTo(rucEntity3.getId());
	}

	@Test
	public void should_get_users_by_role_and_circle() {
		Long idRole = 2L;
		Long idCircle = 2L;
		final List<UserDto> links = rucService.getLinksByRoleAndCircle(idRole, idCircle);

		assertThat(links).hasSize(1);
		assertThat(links.get(0).getId()).isEqualTo(userEntity.getId());
		assertThat(links.get(0).getEmail()).isEqualTo(userEntity.getEmail());
	}

	@Test
	public void should_get_links_by_user_and_circle() {
		Long idUser = 2L;
		Long idCircle = 2L;

		try {
			RucDto link = rucService.getLinkByUserAndCircle(idUser, idCircle);

			assertThat(link).isNotNull();
			assertThat(link.getId()).isEqualTo(rucEntity4.getId());
		} catch (RucNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve link between user and circle");
		}
	}

	@Test
	public void should_throw_RucNotFoundException_with_unknown_user_and_circle() {
		Long idUser = 3L;
		Long idCircle = 3L;

		try {
			rucService.getLinkByUserAndCircle(idUser, idCircle);

			fail("Test failed : an exception should have been thrown when trying to retrieve link with unknown user and circle");
		} catch (RucNotFoundException e) {
		}
	}

	@Test
	public void should_insert_link() {
		when(rucRepository.save(any(RucEntity.class))).thenReturn(rucEntity5);

		RucDto rucDto = RucDto.builder()
				.idRole(rucEntity5.getIdRole())
				.idUser(rucEntity5.getIdUser())
				.idCircle(rucEntity5.getIdCircle())
				.build();

		rucDto = rucService.insertLink(rucDto);

		assertThat(rucDto).isNotNull();
		assertThat(rucDto.getId()).isEqualTo(rucEntity5.getId());

		verify(rucRepository, times(1)).save(any(RucEntity.class));
	}

	@Test
	public void should_delete_link() {
		Long idUser = 1L;
		Long idCircle = 1L;

		rucService.deleteLink(idUser, idCircle);

		verify(rucRepository, times(1)).deleteAllByIdUserAndIdCircle(1L, 1L);
	}

	@Test
	public void should_update_link_with_idRole() {
		when(rucRepository.save(any(RucEntity.class))).thenReturn(rucEntity6);

		Long idUser = 2L;
		Long idCircle = 2L;
		Long idRole = 2L;

		RucDto rucDto = rucService.updateRole(idUser, idCircle, idRole);

		assertThat(rucDto).isNotNull();
		assertThat(rucDto.getId()).isEqualTo(rucEntity6.getId());
		assertThat(rucDto.getIdRole()).isEqualTo(rucEntity6.getIdRole());

		verify(rucRepository, times(1)).findByIdUserAndIdCircle(idUser, idCircle);
		verify(rucRepository, times(1)).save(any(RucEntity.class));
	}

	@Test
	public void should_create_link_when_update_with_idRole_with_unknown_link() {
		when(rucRepository.save(any(RucEntity.class))).thenReturn(rucEntity7);

		Long idUser = 3L;
		Long idCircle = 3L;
		Long idRole = 2L;

		RucDto rucDto = rucService.updateRole(idUser, idCircle, idRole);
		assertThat(rucDto).isNotNull();
		assertThat(rucDto.getId()).isEqualTo(rucEntity7.getId());

		verify(rucRepository, times(1)).findByIdUserAndIdCircle(idUser, idCircle);
		verify(rucRepository, times(1)).save(any(RucEntity.class));
	}

	@Test
	public void should_update_link_with_roleName() {
		when(rucRepository.save(any(RucEntity.class))).thenReturn(rucEntity6);

		Long idUser = 2L;
		Long idCircle = 2L;
		String roleName = roleEntity.getName();

		try {
			RucDto rucDto = rucService.updateRole(idUser, idCircle, roleName);

			assertThat(rucDto).isNotNull();
			assertThat(rucDto.getId()).isEqualTo(rucEntity6.getId());
			assertThat(rucDto.getIdRole()).isEqualTo(rucEntity6.getIdRole());

			verify(rucRepository, times(1)).findByIdUserAndIdCircle(idUser, idCircle);
			verify(roleRepository, times(1)).findByName(roleName);
			verify(rucRepository, times(1)).save(any(RucEntity.class));
		} catch (RoleNotFoundException e) {
			fail("Test fail : an unexpected exception has been thrown when trying to update a link with the roleName");
		}
	}

	@Test
	public void should_create_link_when_update_with_roleName_with_unknown_link() {
		when(rucRepository.save(any(RucEntity.class))).thenReturn(rucEntity7);

		Long idUser = 3L;
		Long idCircle = 3L;
		String roleName = roleEntity.getName();

		try {
			RucDto rucDto = rucService.updateRole(idUser, idCircle, roleName);
			assertThat(rucDto).isNotNull();
			assertThat(rucDto.getId()).isEqualTo(rucEntity7.getId());

			verify(rucRepository, times(1)).findByIdUserAndIdCircle(idUser, idCircle);
			verify(rucRepository, times(1)).save(any(RucEntity.class));
		} catch (RoleNotFoundException e) {
			fail("Test failed : a RucNotFoundException should have been thrown when trying to update a link with unknown user and circle");
		}
	}

	@Test
	public void should_throw_RoleNotFoundException_when_update_with_roleName_with_unknown_link() {
		Long idUser = 2L;
		Long idCircle = 2L;
		String roleName = "UNKNOWN";

		try {
			rucService.updateRole(idUser, idCircle, roleName);

			fail("Test failed : an exception should have been thrown when trying to update a link with unknown user and circle");
		} catch (RoleNotFoundException e) {
			verify(rucRepository, times(1)).findByIdUserAndIdCircle(idUser, idCircle);
			verify(roleRepository, times(1)).findByName(roleName);
		}
	}
}
