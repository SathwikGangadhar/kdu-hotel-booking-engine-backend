package com.kdu.IBE;

import com.kdu.IBE.service.HealthService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HealthCheckControllerTest {
	private final HealthService healthService=new HealthService();

	@Test
	void testHealthCheck() {
		//expected
		HttpStatus statusExpected = HttpStatus.OK;
		Health health = healthService.health();
		//actual
		HttpStatus statusReceived = HttpStatus.OK;
		if (health.getStatus().equals(Status.DOWN)) {
			statusReceived = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		assertThat(statusReceived).isEqualTo(statusExpected);
	}
}
