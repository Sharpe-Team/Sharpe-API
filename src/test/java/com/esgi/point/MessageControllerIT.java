package com.esgi.point;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by rfruitet on 13/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@MessageData
@ActiveProfiles("test")
public class MessageControllerIT {

	@LocalServerPort
	private int localServerPort;

	@Before
	public void init() {
		RestAssured.port = localServerPort;
	}

	@Test
	@Ignore
	public void should_get_all_messages_of_topic_one() {

		when()
				.get("/point?cercle={cercle}", 1L)
		.then()
				.log().all()
				.statusCode(200)
				.body("$", hasSize(3));
	}
}
