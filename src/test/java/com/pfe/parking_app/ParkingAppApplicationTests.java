package com.pfe.parking_app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // 👈 très important
class ParkingAppApplicationTests {

	@Test
	void contextLoads() {
	}
}
