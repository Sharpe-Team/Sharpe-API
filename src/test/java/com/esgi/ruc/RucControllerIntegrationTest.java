package com.esgi.ruc;

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

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RucData
@RoleData
@UserData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class RucControllerIntegrationTest {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	public void should_get_all_links() {
		when()
				.get("/rucs")
		.then()
				.statusCode(200)
				.body("$", hasSize(4));
	}

	@Test
	public void should_get_link_by_role() {
		given()
				.contentType(JSON)
		.when()
				.get("/rucs?role_id={role_id}", 1L)
		.then()
				.statusCode(200)
				.body("$", hasSize(2))
				.body("id", hasItems(1, 4));
	}

	@Test
	public void should_not_found_link_with_unknown_role() {
		given()
				.contentType(JSON)
		.when()
				.get("/rucs?role_id={role_id}", 3L)
		.then()
				.statusCode(200)
				.body("$", hasSize(0));
	}

	@Test
	public void should_get_link_by_user() {
		given()
				.contentType(JSON)
		.when()
				.get("/rucs?user_id={user_id}", 1L)
		.then()
				.statusCode(200)
				.body("$", hasSize(2))
				.body("id", hasItems(1, 2));
	}

	@Test
	public void should_get_link_by_circle() {
		given()
				.contentType(JSON)
		.when()
				.get("/rucs?circle_id={circle_id}", 1L)
		.then()
				.statusCode(200)
				.body("$", hasSize(2))
				.body("id", hasItems(1, 3));
	}

	@Test
	public void should_get_link_by_role_and_user() {
		given()
				.contentType(JSON)
		.when()
				.get("/rucs?role_id={role_id}&user_id={user_id}", 2L, 2L)
		.then()
				.statusCode(200)
				.body("$", hasSize(1))
				.body("id", hasItems(3))
				.body("idCircle", hasItems(1));
	}

	@Test
	public void should_get_users_by_role_and_circle() {
		given()
				.contentType(JSON)
		.when()
				.get("/rucs?role_id={role_id}&circle_id={circle_id}", 2L, 2L)
		.then()
				.statusCode(200)
				.body("$", hasSize(1))
				.body("id", hasItems(1))
				.body("firstname", hasItems("first"))
				.body("lastname", hasItems("lastname"))
				.body("email", hasItems("one@email.com"))
				.body("password", hasItems(""));

	}

	@Test
	public void should_get_link_by_user_and_circle() {
		given()
				.contentType(JSON)
		.when()
				.get("/rucs?user_id={user_id}&circle_id={circle_id}", 2L, 2L)
		.then()
				.statusCode(200)
				.body("id", is(4))
				.body("idRole", is(1));
	}

	@Test
	public void should_insert_link() {
		RucDto rucDto = RucDto.builder()
				.idRole(2L)
				.idUser(2L)
				.idCircle(2L)
				.build();

		given()
				.contentType(JSON)
				.body(rucDto)
		.when()
				.post("/rucs")
		.then()
				.statusCode(201);
	}

	@Test
	public void should_not_insert_link_without_idRole() {
		RucDto rucDto = RucDto.builder()
				.idUser(2L)
				.idCircle(2L)
				.build();

		given()
				.contentType(JSON)
				.body(rucDto)
		.when()
				.post("/rucs")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_not_insert_link_without_idUser() {
		RucDto rucDto = RucDto.builder()
				.idRole(2L)
				.idCircle(2L)
				.build();

		given()
				.contentType(JSON)
				.body(rucDto)
		.when()
				.post("/rucs")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_not_insert_link_without_idCircle() {
		RucDto rucDto = RucDto.builder()
				.idRole(2L)
				.idUser(2L)
				.build();

		given()
				.contentType(JSON)
				.body(rucDto)
		.when()
				.post("/rucs")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_delete_link() {
		given()
				.contentType(JSON)
		.when()
				.delete("/rucs?user_id={user_id}&circle_id={circle_id}", 1L, 1L)
		.then()
				.statusCode(200)
				.body("$", hasSize(1))
				.body("id", hasItems(1));
	}

	@Test
	public void should_not_delete_unknown_link() {
		given()
				.contentType(JSON)
		.when()
				.delete("/rucs?user_id={user_id}&circle_id={circle_id}", 3L, 3L)
		.then()
				.statusCode(200)
				.body("$", hasSize(0));
	}

	@Test
	public void should_update_link_with_idRole() {
		given()
				.contentType(JSON)
		.when()
				.put("/rucs?user_id={user_id}&circle_id={circle_id}&role_id={role_id}", 2L, 2L, 2L)
		.then()
				.statusCode(200)
				.body("id", is(4))
				.body("idUser", is(2))
				.body("idCircle", is(2))
				.body("idRole", is(2));
	}

	@Test
	public void should_not_update_link_with_idRole_with_unknown_user_and_circle() {
		given()
				.contentType(JSON)
		.when()
				.put("/rucs?user_id={user_id}&circle_id={circle_id}&role_id={role_id}", 3L, 3L, 2L)
		.then()
				.statusCode(200)
				.body("idUser", is(3))
				.body("idCircle", is(3))
				.body("idRole", is(2));
	}

	@Test
	public void should_update_link_with_roleName() {
		given()
				.contentType(JSON)
		.when()
				.put("/rucs?user_id={user_id}&circle_id={circle_id}&role_name={role_name}", 2L, 2L, "MODERATOR")
		.then()
				.statusCode(200)
				.body("id", is(4))
				.body("idUser", is(2))
				.body("idCircle", is(2))
				.body("idRole", is(2));
	}

	@Test
	public void should_not_update_link_with_roleName_with_unknown_user_and_circle() {
		given()
				.contentType(JSON)
		.when()
				.put("/rucs?user_id={user_id}&circle_id={circle_id}&role_name={role_name}", 3L, 3L, "MODERATOR")
		.then()
				.statusCode(200)
				.body("idUser", is(3))
				.body("idCircle", is(3))
				.body("idRole", is(2));
	}

	@Test
	public void should_not_update_link_with_roleName_with_unknown_roleName() {
		given()
				.contentType(JSON)
		.when()
				.put("/rucs?user_id={user_id}&circle_id={circle_id}&role_name={role_name}", 2L, 2L, "UNKNOWN")
		.then()
				.statusCode(404);
	}
}
