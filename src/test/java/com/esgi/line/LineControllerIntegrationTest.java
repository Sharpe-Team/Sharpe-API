package com.esgi.line;

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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

/**
 * Created by thomasfouan on 29/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@LineData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_CLASS)
public class LineControllerIntegrationTest {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	public void shouldGetAllLinesOfCircle1() {

		Long idCircle = 1L;

		when()
				.get("/lines?idCircle={id_circle}", idCircle)
		.then()
				.statusCode(200)
				.body("$", hasSize(2));
	}

	@Test
	public void shouldThrowCircleNotFoundExceptionWithUnknownIdCircle() {

		Long idCircle = 3L;

		when()
				.get("/lines?idCircle={id_circle}", idCircle)
		.then()
				.statusCode(404);
	}

	@Test
	public void shouldGetFirstLine() {
		when()
				.get("/lines/{line_id}", 1L)
		.then()
				.statusCode(200)
				.body("id", is(1))
				.body("idCircle", is(1))
				.body("name", is("line1"))
				.body("announcement", is("message1"));
	}

	@Test
	public void shouldNotFoundUnknownLine() {
		when()
				.get("/lines/{line_id}", 4L)
		.then()
				.statusCode(404);
	}

	@Test
	public void shouldInsertLine() {
		LineDto lineDto = LineDto.builder()
				.id(4L)
				.idCircle(2L)
				.name("line4")
				.announcement("message4")
				.build();

		given()
				.contentType(JSON)
				.body(lineDto)
		.when()
				.post("/lines")
		.then()
				.statusCode(201);
	}

	@Test
	public void shouldThrowCircleValidationExceptionWithoutIdCircle() {
		LineDto lineDto = LineDto.builder()
				.name("line5")
				.announcement("message5")
				.build();

		given()
				.contentType(JSON)
				.body(lineDto)
		.when()
				.post("/lines")
		.then()
				.statusCode(400);
	}

	@Test
	public void shouldThrowCircleValidationExceptionWithoutName() {
		LineDto lineDto = LineDto.builder()
				.idCircle(3L)
				.announcement("message6")
				.build();

		given()
				.contentType(JSON)
				.body(lineDto)
		.when()
				.post("/lines")
		.then()
				.statusCode(400);
	}
}
