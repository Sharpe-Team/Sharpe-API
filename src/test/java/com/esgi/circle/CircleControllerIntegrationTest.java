package com.esgi.circle;

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
 * Created by thomasfouan on 29/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@CircleData
@ActiveProfiles("test")
public class CircleControllerIntegrationTest {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	public void shouldGetAllCircles() {
		when()
				.get("/circles")
		.then()
				.statusCode(200)
				.body("$", hasSize(3));
	}

	@Test
	public void shouldGetFirstCircle() {
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
	public void shouldNotFoundUnkownCircle() {
		when()
				.get("/circles/{circle_id}", 4L)
		.then()
				.statusCode(404);
	}

	@Test
	public void shouldInsertCircle() {
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
				.statusCode(201);
	}

	@Test
	public void shouldThrowCircleValidationExceptionForEmptyName() {
		CircleDto user = CircleDto.builder()
				.name("")
				.pictureUrl("picture4.png")
				.bannerPictureUrl("bannerPicture4.png")
				.build();

		given()
				.contentType(JSON)
				.body(user)
		.when()
				.post("/circles")
		.then()
				.statusCode(400);
	}
}
