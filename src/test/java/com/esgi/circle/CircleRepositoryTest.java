package com.esgi.circle;

import com.esgi.user.UserData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by thomasfouan on 29/03/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@UserData
@ActiveProfiles("test")
public class CircleRepositoryTest {

	@Autowired
	private CircleRepository circleRepository;

	// No implemented methods in CircleRepository to test...
	@Test
	public void shoudlTestSomething() {

	}
}
