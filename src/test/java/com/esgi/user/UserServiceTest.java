package com.esgi.user;

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
 * Created by thomasfouan on 04/03/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	private UserEntity user1;

	private UserEntity user2;

	private UserEntity user3;

	private UserEntity user4;

	public void initUsers() {

		user1 = UserEntity.builder()
				.id(1L)
				.firstname("first")
				.password("password1")
				.build();

		user2 = UserEntity.builder()
				.id(2L)
				.firstname("second")
				.password("password2")
				.build();

		user3 = UserEntity.builder()
				.id(3L)
				.firstname("third")
				.password("password3")
				.build();

		user4 = UserEntity.builder()
				.id(4L)
				.firstname("fourth")
				.password("password4")
				.build();
	}

	@Before
	public void configureMock() {

		initUsers();

		ArrayList<UserEntity> list = new ArrayList<>();
		list.add(user3);

		when(userRepository.findByUsername(user3.getFirstname())).thenReturn(list);

		list = new ArrayList<>();
		list.add(user1);
		list.add(user2);
		list.add(user3);

		when(userRepository.findAll()).thenReturn(list);

		when(userRepository.findOne(user2.getId())).thenReturn(user2);

		when(userRepository.findOne(4L)).thenReturn(null);

		when(userRepository.findByUsername("fourth")).thenReturn(new ArrayList<>());

		when(userRepository.save(any(UserEntity.class))).thenReturn(user4);
	}

	@Test
	public void shouldGetAllUsers() {

		List<UserEntity> allUsers = userService.getAllUsers();

		assertThat(allUsers).hasSize(3);
	}

	@Test
	public void shouldGetOneUserWithId() {

		Long id = user2.getId();

		UserEntity user;
		try {
			user = userService.getUser(id);

			assertThat(user).isNotNull();
			assertThat(user.getId()).isEqualTo(user2.getId());
			assertThat(user.getFirstname()).isEqualTo(user2.getFirstname());
			assertThat(user.getPassword()).isEqualTo(user2.getPassword());
		} catch (UserNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve on user with id = " + id);
		}
	}

	@Test
	public void shouldThrowUserNotFoundExceptionWithUnknownId() {

		Long id = 4L;

		try {
			userService.getUser(id);

			fail("Test failed : an exception should have been thrown when trying to retrieve one user with id = " + id);
		} catch (UserNotFoundException e) {
		}
	}

	@Test
	public void shouldGetOneUserByUsername() {

		String username = user3.getFirstname();

		try {
			UserEntity user = userService.getUserByUsername(username);

			assertThat(user).isNotNull();
			assertThat(user.getId()).isEqualTo(user3.getId());
			assertThat(user.getFirstname()).isEqualTo(user3.getFirstname());
			assertThat(user.getPassword()).isEqualTo(user3.getPassword());
		} catch (UserNotFoundException e) {
			fail("Test failed : an unexpected exception has been thrown when trying to retrieve one user with username = '" + username + "'");
		}
	}

	@Test
	public void shouldThrowUserNotFoundExceptionWithUnknownUsername() {

		String username = "fourth";

		try {
			userService.getUserByUsername(username);

			fail("Test failed : an exception should have been thrown when trying to retrieve one user with username = '" + username + "'");
		} catch (UserNotFoundException e) {
		}
	}

	@Test
	public void shouldInsertUser() {

		UserEntity user = UserEntity.builder()
				.firstname("fourth")
				.password("password4")
				.build();

		user = userService.insertUser(user);

		assertThat(user.getId()).isEqualTo(4);

		verify(userRepository, times(1)).save(any(UserEntity.class));
	}
}

