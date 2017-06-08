package com.esgi.user;

import com.esgi.role.RoleEntity;
import com.esgi.role.RoleRepository;
import com.esgi.ruc.RucEntity;
import com.esgi.ruc.RucRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RucRepository rucRepository;

	@Mock
	private RoleRepository roleRepository;

	private UserEntity user1;
	private UserEntity user2;
	private UserEntity user3;
	private UserEntity user4;

	private Date date;

	private RucEntity rucEntity;
	private RoleEntity roleEntity;

	public void initUsers() {

		date = new Date();

		user1 = UserEntity.builder()
				.id(1L)
				.firstname("first")
				.lastname("lastname1")
				.email("first@email.com")
				.password("password1")
				.profilePicture("url/picture1.png")
				.created(date)
				.updated(date)
				.build();

		user2 = UserEntity.builder()
				.id(2L)
				.firstname("second")
				.lastname("lastname2")
				.email("second@email.com")
				.password("password2")
				.profilePicture("url/picture2.png")
				.created(date)
				.updated(date)
				.build();

		user3 = UserEntity.builder()
				.id(3L)
				.firstname("third")
				.lastname("lastname1")
				.email("tird@email.com")
				.password("password3")
				.profilePicture("url/picture3.png")
				.created(date)
				.updated(date)
				.build();

		user4 = UserEntity.builder()
				.id(4L)
				.firstname("fourth")
				.lastname("lastname4")
				.email("fourth@email.com")
				.password("password4")
				.profilePicture("url/picture4.png")
				.created(date)
				.updated(date)
				.build();

		rucEntity = RucEntity.builder()
				.id(1L)
				.idRole(1L)
				.idUser(1L)
				.idCircle(1L)
				.build();

		roleEntity = RoleEntity.builder()
				.id(1L)
				.name("USER")
				.build();
	}

	@Before
	public void configureMock() {
		initUsers();

		List<UserEntity> list = new ArrayList<>();
		list.add(user3);

		when(userRepository.findByFirstname(user3.getFirstname())).thenReturn(list);

		list = new ArrayList<>();
		list.add(user1);
		list.add(user2);
		list.add(user3);

		when(userRepository.findAll()).thenReturn(list);
		when(userRepository.findOne(user2.getId())).thenReturn(user2);
		when(userRepository.findOne(4L)).thenReturn(null);
		when(userRepository.findByFirstname("fourth")).thenReturn(new ArrayList<>());
		when(userRepository.save(any(UserEntity.class))).thenReturn(user4);

		List<RucEntity> listRuc = new ArrayList<>();
		listRuc.add(rucEntity);

		when(rucRepository.findByIdUser(2L)).thenReturn(listRuc);
		when(rucRepository.findByIdUser(3L)).thenReturn(listRuc);
		when(roleRepository.findOne(1L)).thenReturn(roleEntity);
	}

	@Test
	public void should_get_all_users() {
		List<UserDto> allUsers = userService.getAllUsers();

		assertThat(allUsers).hasSize(3);
	}

	@Test
	public void should_get_one_user_with_id() {
		Long id = user2.getId();

		UserDto user;
		try {
			user = userService.getUser(id);

			assertThat(user).isNotNull();
			assertThat(user.getId()).isEqualTo(user2.getId());
			assertThat(user.getFirstname()).isEqualTo(user2.getFirstname());
			assertThat(user.getPassword()).isEqualTo("");
			assertThat(user.getCirclesRole()).isNotNull();
			assertThat(user.getCirclesRole().size()).isEqualTo(1);
			assertThat(user.getCirclesRole().get(1L)).isEqualTo(roleEntity.getName());
		} catch (UserNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve one user with id = " + id);
		}
	}

	@Test
	public void should_throw_UserNotFoundException_with_unknown_id() {
		Long id = 4L;

		try {
			userService.getUser(id);

			fail("Test failed : an exception should have been thrown when trying to retrieve one user with id = " + id);
		} catch (UserNotFoundException e) {
		}
	}

	@Test
	public void should_get_one_user_by_firstname() {
		String username = user3.getFirstname();

		try {
			UserDto user = userService.getUserByUsername(username);

			assertThat(user).isNotNull();
			assertThat(user.getId()).isEqualTo(user3.getId());
			assertThat(user.getFirstname()).isEqualTo(user3.getFirstname());
			assertThat(user.getPassword()).isEqualTo("");
			assertThat(user.getCirclesRole()).isNotNull();
			assertThat(user.getCirclesRole().size()).isEqualTo(1);
			assertThat(user.getCirclesRole().get(1L)).isEqualTo(roleEntity.getName());
		} catch (UserNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve one user with username = '" + username + "'");
		}
	}

	@Test
	public void should_throw_UserNotFoundException_with_unknown_firstname() {
		String username = "fourth";

		try {
			userService.getUserByUsername(username);

			fail("Test failed : an exception should have been thrown when trying to retrieve one user with username = '" + username + "'");
		} catch (UserNotFoundException e) {
		}
	}

	@Test
	public void should_insert_user() {
		UserDto user = UserDto.builder()
				.firstname("fourth")
				.password("password4")
				.build();

		try {
			user = userService.insertUser(user);

			assertThat(user.getId()).isEqualTo(4);
			assertThat(user.getCirclesRole()).isNotNull();
			assertThat(user.getCirclesRole().size()).isEqualTo(0);
			verify(userRepository, times(1)).save(any(UserEntity.class));
		} catch (EmailAddressAlreadyExistsException e) {
			fail("An unexpected exception has been thrown...");
		}
	}
}

