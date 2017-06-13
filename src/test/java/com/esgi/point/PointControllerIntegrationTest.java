package com.esgi.point;

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
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by rfruitet on 13/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@PointData
@ActiveProfiles("test")
public class PointControllerIntegrationTest {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	public void should_get_all_points_of_topic_one() {
		when()
				.get("/points?idLine={line}", 1L)
		.then()
				.log().all()
				.statusCode(200)
				.body("$", hasSize(3));
	}

	@Test
	public void should_insert_one_point_in_circle() {
		Date date = new Date();

		PointDto pointDto = PointDto.builder()
				.id(1L)
				.idLine(2L)
				.idUser(8L)
				.content("My message")
				.created(date)
				.updated(date)
				.build();

		given()
				.contentType(JSON)
				.body(pointDto)
		.when()
				.post("/points")
		.then()
				.log()
				.all()
				.statusCode(201);
	}

	@Test
	public void should_throw_PointValidationException_for_empty_id_user() {
		Date date = new Date();

		PointDto pointDto = PointDto.builder()
				.id(1L)
				.idLine(2L)
				.content("my message")
				.created(date)
				.updated(date)
				.build();

		given()
				.contentType(JSON)
				.body(pointDto)
		.when()
				.post("/points")
		.then()
				.log()
				.all()
				.statusCode(400);
	}

	@Test
	public void should_throw_PointValidationException_for_empty_content() {
		Date date = new Date();

		PointDto pointDto = PointDto.builder()
				.id(1L)
				.idLine(2L)
				.idUser(8L)
				.content("")
				.created(date)
				.updated(date)
				.build();

		given()
				.contentType(JSON)
				.body(pointDto)
		.when()
				.post("/points")
		.then()
				.log()
				.all()
				.statusCode(400);
	}

	@Test
	public void should_delete_point() {

		when()
				.delete("/points/{point_id}", 1L)
		.then()
				.statusCode(204);
	}
}
