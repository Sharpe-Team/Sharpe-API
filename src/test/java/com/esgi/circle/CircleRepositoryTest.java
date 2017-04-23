package com.esgi.circle;

import com.esgi.user.UserData;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

/**
 * Created by thomasfouan on 29/03/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@CircleData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_CLASS)
public class CircleRepositoryTest {

	@Autowired
	private CircleRepository circleRepository;

	// No implemented methods in CircleRepository to test...
	@Ignore
	@Test
	public void shoudl_test_something() {

	}
}
