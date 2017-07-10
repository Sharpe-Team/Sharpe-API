package com.esgi.joining_request;

import com.esgi.role.RoleEntity;
import com.esgi.role.RoleNotFoundException;
import com.esgi.role.RoleRepository;
import com.esgi.ruc.RucEntity;
import com.esgi.ruc.RucRepository;
import com.esgi.user.*;
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
 * Created by thomasfouan on 10/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class JoiningRequestServiceTest {

	@InjectMocks
	private JoiningRequestServiceImpl joiningRequestService;

	@Mock
	private JoiningRequestRepository joiningRequestRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private RucRepository rucRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserServiceImpl userService;

	private JoiningRequestEntity joiningRequestEntity1;
	private JoiningRequestEntity joiningRequestEntity2;
	private JoiningRequestEntity joiningRequestEntity3;
	private JoiningRequestEntity joiningRequestEntity4;

	private RoleEntity roleEntity;

	private RucEntity rucEntity;

	private UserDto userDto;
	private UserDto userDto2;

	private void initEntities() {

		joiningRequestEntity1 = JoiningRequestEntity.builder()
				.id(1L)
				.idUser(1L)
				.idCircle(1L)
				.build();

		joiningRequestEntity2 = JoiningRequestEntity.builder()
				.id(2L)
				.idUser(1L)
				.idCircle(2L)
				.build();

		joiningRequestEntity3 = JoiningRequestEntity.builder()
				.id(3L)
				.idUser(2L)
				.idCircle(1L)
				.build();

		joiningRequestEntity4 = JoiningRequestEntity.builder()
				.id(4L)
				.idUser(2L)
				.idCircle(2L)
				.build();

		roleEntity = RoleEntity.builder()
				.id(1L)
				.name("USER")
				.build();

		rucEntity = RucEntity.builder()
				.id(1L)
				.idUser(2L)
				.idCircle(2L)
				.idRole(1L)
				.build();

		userDto = UserDto.builder()
				.id(1L)
				.email("third@third.com")
				.password("azertyuiop")
				.build();

		userDto2 = UserDto.builder()
				.id(2L)
				.email("third@third.com")
				.password("azertyuiop")
				.build();
	}

	@Before
	public void configureMock() throws UserNotFoundException {
		initEntities();

		List<JoiningRequestEntity> listAll = new ArrayList<>();
		listAll.add(joiningRequestEntity1);
		listAll.add(joiningRequestEntity2);
		listAll.add(joiningRequestEntity3);

		List<JoiningRequestEntity> listUser1 = new ArrayList<>();
		listUser1.add(joiningRequestEntity1);
		listUser1.add(joiningRequestEntity2);

		List<JoiningRequestEntity> listCircle1 = new ArrayList<>();
		listCircle1.add(joiningRequestEntity1);
		listCircle1.add(joiningRequestEntity3);

		List<RoleEntity> listRole = new ArrayList<>();
		listRole.add(roleEntity);

		when(joiningRequestRepository.findAll()).thenReturn(listAll);
		when(joiningRequestRepository.findByIdUser(1L)).thenReturn(listUser1);
		when(joiningRequestRepository.findByIdCircle(1L)).thenReturn(listCircle1);
		when(joiningRequestRepository.save(any(JoiningRequestEntity.class))).thenReturn(joiningRequestEntity4);
		when(joiningRequestRepository.findOne(2L)).thenReturn(joiningRequestEntity2);
		when(joiningRequestRepository.findOne(4L)).thenReturn(null);

		when(roleRepository.findByName("USER")).thenReturn(listRole);

		when(rucRepository.save(any(RucEntity.class))).thenReturn(rucEntity);
		when(rucRepository.findByIdUser(anyLong())).thenReturn(new ArrayList<>());

		when(userService.getUser(1L)).thenReturn(userDto);
		when(userService.getUser(2L)).thenReturn(userDto2);
	}

	@Test
	public void should_get_all_joining_requests() {
		List<JoiningRequestDto> joiningRequests = joiningRequestService.getAllJoiningRequests();

		assertThat(joiningRequests).isNotNull();
		assertThat(joiningRequests).hasSize(3);
	}

	@Test
	public void should_get_joining_requests_by_user() {
		Long id = 1L;
		List<JoiningRequestDto> joiningRequests = joiningRequestService.getJoiningRequestsByUser(id);

		assertThat(joiningRequests).isNotNull();
		assertThat(joiningRequests).hasSize(2);
		assertThat(joiningRequests.get(0).getId()).isEqualTo(1L);
		assertThat(joiningRequests.get(1).getId()).isEqualTo(2L);
	}

	@Test
	public void should_get_joining_requests_by_circle() {
		Long id = 1L;
		List<UserDto> users = joiningRequestService.getJoiningRequestsByCircle(id);

		assertThat(users).isNotNull();
		assertThat(users).hasSize(2);
		assertThat(users.get(0)).isNotNull();
		assertThat(users.get(0).getId()).isEqualTo(1L);
		assertThat(users.get(1)).isNotNull();
		assertThat(users.get(1).getId()).isEqualTo(2L);
	}

	@Test
	public void should_insert_joining_request() {
		JoiningRequestDto joiningRequestDto = JoiningRequestDto.builder()
				.idUser(joiningRequestEntity4.getIdUser())
				.idCircle(joiningRequestEntity4.getIdCircle())
				.build();

		joiningRequestDto = joiningRequestService.insertJoiningRequest(joiningRequestDto);

		assertThat(joiningRequestDto).isNotNull();
		assertThat(joiningRequestDto.getId()).isEqualTo(joiningRequestEntity4.getId());

		verify(joiningRequestRepository, times(1)).save(any(JoiningRequestEntity.class));
	}

	@Test
	public void should_delete_not_accepted_joining_request() {
		Long id = 2L;
		boolean isAccepted = false;

		try {
			joiningRequestService.deleteJoiningRequest(id, isAccepted);

			verify(joiningRequestRepository, times(1)).findOne(id);
			verify(joiningRequestRepository, times(1)).delete(id);
			verify(roleRepository, times(0)).findByName("USER");
			verify(rucRepository, times(0)).save(any(RucEntity.class));
		} catch (JoiningRequestNotFoundException | RoleNotFoundException e) {
			fail("An unexpected exception has been thrown while deleting a not accepted joining request");
		}
	}

	@Test
	public void should_delete_accepted_joining_request() {
		Long id = 2L;
		boolean isAccepted = true;

		try {
			joiningRequestService.deleteJoiningRequest(id, isAccepted);

			verify(joiningRequestRepository, times(1)).findOne(id);
			verify(joiningRequestRepository, times(1)).delete(id);
			verify(roleRepository, times(1)).findByName("USER");
			verify(rucRepository, times(1)).save(any(RucEntity.class));
		} catch (JoiningRequestNotFoundException | RoleNotFoundException e) {
			fail("An unexpected exception has been thrown while deleting an accepted joining request");
		}
	}

	@Test
	public void should_throw_JoiningRequestNotFoundException_when_delete_unknown_joining_request() {
		Long id = 4L;
		boolean isAccepted = true;

		try {
			joiningRequestService.deleteJoiningRequest(id, isAccepted);
			fail("A JoiningRequestNotFoundException should have been thrown while getting unknown joining request");
		} catch (RoleNotFoundException e) {
			fail("An unexpected exception has been thrown while deleting an unknown joining request");
		} catch (JoiningRequestNotFoundException e) {
			verify(joiningRequestRepository, times(1)).findOne(id);
			verify(joiningRequestRepository, times(0)).delete(id);
			verify(roleRepository, times(0)).findByName("USER");
			verify(rucRepository, times(0)).save(any(RucEntity.class));
		}
	}
}
