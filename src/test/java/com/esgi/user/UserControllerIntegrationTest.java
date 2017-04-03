package com.esgi.user;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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
	public void should_get_all_users() {
		when()
				.get("/users")
		.then()
				.statusCode(200)
				.body("$", hasSize(3));
	}

	@Test
	public void should_get_first_user() {
		when()
				.get("/users/{user_id}", 1)
		.then()
				.statusCode(200)
				.body("id", is(1))
				.body("firstname", is("first"))
				.body("password", is("password1"));
	}

	@Test
	public void should_not_found_unknown_user() {
		when()
				.get("/users/{user_id}", 4)
		.then()
				.statusCode(404);
	}

	@Test
	public void should_get_second_user_by_username() {
		when()
				.get("/users?firstname={firstname}", "second")
		.then()
				.statusCode(200)
				.body("id", is(2))
				.body("firstname", is("second"))
				.body("password", is("password2"));
	}

	@Test
	public void should_not_found_unknown_user_by_username() {
		when()
				.get("/users?firstname={firstname}", "fourth")
		.then()
				.statusCode(404);
	}

	@Test
	public void should_insert_user() {
		Date date = new Date();

		UserEntity user = UserEntity.builder()
				.firstname("fourth")
				.lastname("lastname4")
				.email("fourth@email.com")
				.password("password4")
				.profilePicture("url/picture4.png")
				.created(date)
				.updated(date)
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
	public void should_throw_UserValidationException_for_empty_username() {
		Date date = new Date();

		UserEntity user = UserEntity.builder()
				.firstname("")
				.lastname("lastname4")
				.email("fourth@email.com")
				.password("password4")
				.profilePicture("url/picture4.png")
				.created(date).updated(date)
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
	public void should_throw_UserValidationException_for_empty_password() {
		Date date = new Date();
		UserEntity user = UserEntity.builder()
				.firstname("fourth")
				.lastname("lastname4")
				.email("fourth@email.com")
				.password("")
				.profilePicture("url/picture4.png")
				.created(date)
				.updated(date)
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
	public void should_throw_UserValidationException_for_short_password() {
		Date date = new Date();

		UserEntity user = UserEntity.builder()
				.firstname("fourth")
				.lastname("lastname4")
				.email("fourthemail.com")
				.password("short")
				.profilePicture("url/picture4.png")
				.created(date)
				.updated(date)
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
	public void should_throw_UserValidationException_for_invalid_email() {
		Date date = new Date();

		String invalidEmail = "fourthemail.com";

		UserEntity user = UserEntity.builder()
				.firstname("fourth")
				.lastname("lastname4")
				.email(invalidEmail)
				.password("password4")
				.profilePicture("url/picture4.png")
				.created(date)
				.updated(date)
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
