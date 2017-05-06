package com.esgi.circle;

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
 * Created by thomasfouan on 29/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@CircleData
@PrivateCircleData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class CircleControllerIntegrationTest {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	public void should_get_all_circles() {
		when()
				.get("/circles")
		.then()
				.statusCode(200)
				.body("$", hasSize(3));
	}

	@Test
	public void should_get_first_circle() {
		when()
				.get("/circles/{circle_id}", 1L)
		.then()
				.statusCode(200)
				.body("id", is(1))
				.body("name", is("circle1"))
				.body("pictureUrl", is("picture1.png"))
				.body("bannerPictureUrl", is("bannerPicture1.png"));
	}

	@Test
	public void should_not_found_unknown_circle() {
		when()
				.get("/circles/{circle_id}", 4L)
		.then()
				.statusCode(404);
	}

	@Test
	public void should_insert_circle() {
		CircleDto circleDto = CircleDto.builder()
				.name("circle4")
				.pictureUrl("picture4.png")
				.bannerPictureUrl("bannerPicture4.png")
				.type((short) 1)
				.build();

		given()
				.contentType(JSON)
				.body(circleDto)
		.when()
				.post("/circles")
		.then()
				.statusCode(201);
	}

	@Test
	public void should_throw_CircleValidationException_for_empty_name() {
		CircleDto circleDto = CircleDto.builder()
				.name("")
				.pictureUrl("picture4.png")
				.bannerPictureUrl("bannerPicture4.png")
				.type((short) 1)
				.build();

		given()
				.contentType(JSON)
				.body(circleDto)
		.when()
				.post("/circles")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_throw_CircleValidationException_for_null_type() {
		CircleDto circleDto = CircleDto.builder()
				.name("circle4")
				.pictureUrl("picture4.png")
				.bannerPictureUrl("bannerPicture4.png")
				.build();

		given()
				.contentType(JSON)
				.body(circleDto)
		.when()
				.post("/circles")
		.then()
				.statusCode(400);
	}

	@Test
	public void should_get_existing_private_circle() {

		when()
				.get("/circles/private?idUser1=" + 1 + "&idUser2=" + 2)
		.then()
				.statusCode(200)
				.body("id", is(3))
				.body("name", is("circle3"))
				.body("pictureUrl", is("picture3.png"))
				.body("bannerPictureUrl", is("bannerPicture3.png"))
				.body("type", is(2));
	}

	@Test
	public void should_get_existing_private_circle_with_ids_switched() {

		when()
				.get("/circles/private?idUser1=" + 2 + "&idUser2=" + 1)
		.then()
				.statusCode(200)
				.body("id", is(3))
				.body("name", is("circle3"))
				.body("pictureUrl", is("picture3.png"))
				.body("bannerPictureUrl", is("bannerPicture3.png"))
				.body("type", is(2));
	}

	@Test
	public void should_create_new_private_circle() {

		when()
				.get("/circles/private?idUser1=" + 2 + "&idUser2=" + 3)
		.then()
				.statusCode(200)
				.body("id", greaterThan(3))
				.body("type", is(2));
	}

	@Test
	public void should_get_all_public_circles() {

		when()
				.get("/circles/publics")
		.then()
				.statusCode(200)
				.body("$", hasSize(2))
				.body("id", hasItems(1, 2));
	}
}
