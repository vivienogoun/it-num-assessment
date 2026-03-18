package com.vogoun.it_num_assessment;

import org.springframework.boot.SpringApplication;

public class TestItNumAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.from(ItNumAssessmentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
