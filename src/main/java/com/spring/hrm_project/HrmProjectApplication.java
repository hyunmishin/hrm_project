package com.spring.hrm_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//(exclude = {SecurityAutoConfiguration.class})
public class HrmProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmProjectApplication.class, args);
	}

}
