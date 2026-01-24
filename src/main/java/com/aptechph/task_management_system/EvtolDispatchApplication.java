package com.aptechph.task_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EvtolDispatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvtolDispatchApplication.class, args);
	}

}
