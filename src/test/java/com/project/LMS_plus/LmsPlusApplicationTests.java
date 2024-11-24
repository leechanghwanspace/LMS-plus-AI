package com.project.LMS_plus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class LmsPlusApplicationTests {

	@Value("${frontend.url}")
	private String frontendUrl;

	@Test
	void contextLoads() {
	}

	@Test
	void testFrontendUrl() {
		System.out.println("Frontend URL: " + frontendUrl);
		assertThat(frontendUrl).isEqualTo("http://localhost:3000"); // 예상 값과 비교
	}
}
