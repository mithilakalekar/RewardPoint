package com.infy.AssingmentApplicationTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AssingmentApplicationTests {

	@Test
	void contextLoads() {
		
		Object actual = new Object();
		Assertions.assertNotNull(actual);
		
	}

}
