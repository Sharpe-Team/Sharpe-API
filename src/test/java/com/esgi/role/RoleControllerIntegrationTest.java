package com.esgi.role;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RoleData
@ActiveProfiles("test")
public class RoleControllerIntegrationTest {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	public void should_get_all_roles() {
		when()
				.get("/roles")
		.then()
				.statusCode(200)
				.body("$", hasSize(2));
	}

	@Test
	public void should_get_first_role() {
		when()
				.get("/roles/{role_id}", 1)
		.then()
				.statusCode(200)
				.body("id", is(1))
				.body("name", is("USER"));
	}

	@Test
	public void should_not_found_unknown_role() {
		when()
				.get("/roles/{role_id}", 3)
		.then()
				.statusCode(404);
	}

	@Test
	public void should_get_role_by_name() {
		when()
				.get("/roles?name={name}", "MODERATOR")
		.then()
				.statusCode(200)
				.body("id", is(2))
				.body("name", is("MODERATOR"));
	}

	@Test
	public void should_not_found_role_by_unknown_name() {
		when()
				.get("/roles?name={name}", "UNKNOWN")
		.then()
				.statusCode(404);
	}

	@Test
	public void should_insert_role() {
		RoleDto roleDto = RoleDto.builder()
				.name("NEW")
				.build();

		given()
				.contentType(JSON)
				.body(roleDto)
		.when()
				.post("/roles")
		.then()
				.statusCode(201);
	}

	@Test
	public void should_not_insert_role_without_name() {
		RoleDto roleDto = RoleDto.builder()
				.build();

		given()
				.contentType(JSON)
				.body(roleDto)
		.when()
				.post("/roles")
		.then()
				.statusCode(400);
	}
}
