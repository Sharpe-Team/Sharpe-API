package com.esgi.user;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@UserData
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	public void shouldGetAllUsers() {
		when()
				.get("/users")
		.then()
				.statusCode(200)
				.body("$", hasSize(3));
	}

	@Test
	public void shouldGetFirstUser() {
		when()
				.get("/users/{user_id}", 1)
		.then()
				.statusCode(200)
				.body("id", is(1))
				.body("username", is("first"))
				.body("password", is("password1"));
	}

	@Test
	public void shouldNotFoundUnknownUser() {
		when()
				.get("/users/{user_id}", 4)
		.then()
				.statusCode(404);
	}

	@Test
	public void shouldGetSecondUserByUsername() {
		when()
				.get("/users?username={username}", "second")
		.then()
				.statusCode(200)
				.body("id", is(2))
				.body("username", is("second"))
				.body("password", is("password1"));
	}

	@Test
	public void shouldNotFoundUnknownUserByUsername() {
		when()
				.get("/users?username={username}", "fourth")
		.then()
				.statusCode(404);
	}

	@Test
	public void shouldInsertUser() {
		UserEntity user = UserEntity.builder()
				.firstname("fourth")
				.password("password4")
				.build();

		given()
				.contentType(JSON)
				.body(user)
		.when()
				.post("/users")
		.then()
				.statusCode(201);
	}

	@Test
	public void shouldThrowUserValidationExceptionForEmptyUsername() {
		UserEntity user = UserEntity.builder()
				.firstname("")
				.password("password4")
				.build();

		given()
				.contentType(JSON)
				.body(user)
		.when()
				.post("/users")
		.then()
				.statusCode(400);
	}

	@Test
	public void shouldThrowUserValidationExceptionForEmptyPassword() {
		UserEntity user = UserEntity.builder()
				.firstname("fourth")
				.password("")
				.build();

		given()
				.contentType(JSON)
				.body(user)
		.when()
				.post("/users")
		.then()
				.statusCode(400);
	}

	@Test
	public void shouldThrowUserValidationExceptionForShortPassword() {
		UserEntity user = UserEntity.builder()
				.firstname("fourth")
				.password("short")
				.build();

		given()
				.contentType(JSON)
				.body(user)
		.when()
				.post("/users")
		.then()
				.statusCode(400);
	}
}
