package com.vogoun.it_num_assessment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ItNumAssessmentApplicationTests {

	@Test
	void contextLoads() {
	}

}
