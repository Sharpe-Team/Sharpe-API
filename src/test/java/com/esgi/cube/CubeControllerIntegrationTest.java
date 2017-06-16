package com.esgi.cube;

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
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by thomasfouan on 16/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@CubeData
@ActiveProfiles("test")
public class CubeControllerIntegrationTest {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	public void should_get_cubes_of_line_1() {
		when()
				.get("/cubes?line_id={id_line}", 1L)
		.then()
				.statusCode(200)
				.body("$", hasSize(2))
				.body("id", hasItems(1, 2));
	}

	@Test
	public void should_not_get_cubes_for_unknown_line() {
		when()
				.get("/cubes?line_id={id_line}", 3L)
		.then()
				.statusCode(200)
				.body("$", hasSize(0));
	}

	@Test
	public void should_insert_cube() {
		CubeDto cubeDto = CubeDto.builder()
				.idLine(2L)
				.idUser(2L)
				.url("./uploads/picture.jpg")
				.created(new Date())
				.build();

		given()
				.contentType(JSON)
				.body(cubeDto)
		.when()
				.post("/cubes")
		.then()
				.statusCode(201);
	}

	@Test
	public void should_not_insert_cube_without_idLine() {
		CubeDto cubeDto = CubeDto.builder()
				.idUser(2L)
				.url("./uploads/picture.jpg")
				.created(new Date())
				.build();

		given()
				.contentType(JSON)
				.body(cubeDto)
		.when()
				.post("/cubes")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_not_insert_cube_without_idUser() {
		CubeDto cubeDto = CubeDto.builder()
				.idLine(2L)
				.url("./uploads/picture.jpg")
				.created(new Date())
				.build();

		given()
				.contentType(JSON)
				.body(cubeDto)
		.when()
				.post("/cubes")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_not_insert_cube_without_url() {
		CubeDto cubeDto = CubeDto.builder()
				.idLine(2L)
				.idUser(2L)
				.created(new Date())
				.build();

		given()
				.contentType(JSON)
				.body(cubeDto)
		.when()
				.post("/cubes")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_delete_cube() {
		when()
				.delete("/cubes/{cube_id}", 1L)
		.then()
				.statusCode(204);
	}
}
