package com.esgi.joining_request;

import com.esgi.role.RoleData;
import com.esgi.user.UserData;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by thomasfouan on 10/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@JoiningRequestData
@RoleData
@UserData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class JoiningRequestControllerIntegrationTest {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	public void should_get_all_joining_requests() {
		when()
				.get("/joining-requests")
		.then()
				.statusCode(200)
				.body("$", hasSize(3));
	}

	@Test
	public void should_get_joining_requests_by_user() {
		when()
				.get("/joining-requests?user_id={user_id}", 1L)
		.then()
				.statusCode(200)
				.body("$", hasSize(2))
				.body("id", hasItems(1, 2));
	}

	@Test
	public void should_get_joining_requests_by_circle() {
		when()
				.get("/joining-requests?circle_id={circle_id}", 1L)
		.then()
				.statusCode(200)
				.body("$", hasSize(2))
				.body("id", hasItems(1, 2));
	}

	@Test
	public void should_insert_joining_request() {
		JoiningRequestDto joiningRequestDto = JoiningRequestDto.builder()
				.idUser(2L)
				.idCircle(2L)
				.build();

		given()
				.contentType(JSON)
				.body(joiningRequestDto)
		.when()
				.post("/joining-requests")
		.then()
				.statusCode(201);
	}

	@Test
	public void should_not_insert_joining_request_without_idUser() {
		JoiningRequestDto joiningRequestDto = JoiningRequestDto.builder()
				.idCircle(2L)
				.build();

		given()
				.contentType(JSON)
				.body(joiningRequestDto)
		.when()
				.post("/joining-requests")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_not_insert_joining_request_without_idCircle() {
		JoiningRequestDto joiningRequestDto = JoiningRequestDto.builder()
				.idUser(2L)
				.build();

		given()
				.contentType(JSON)
				.body(joiningRequestDto)
		.when()
				.post("/joining-requests")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_delete_not_accepted_joining_request() {
		when()
				.delete("/joining-requests/{joining_request_id}?accepted={accepted}", 2L, false)
		.then()
				.statusCode(204);
	}

	@Test
	public void should_delete_accepted_joining_request() {
		when()
				.delete("/joining-requests/{joining_request_id}?accepted={accepted}", 2L, true)
		.then()
				.statusCode(204);
	}

	@Test
	public void should_throw_JoiningRequestNotFoundException_when_delete_unknown_joining_request() {
		when()
				.delete("/joining-requests/{joining_request_id}?accepted={accepted}", 4L, true)
		.then()
				.statusCode(404);
	}
}
