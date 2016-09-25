package com.isaac.hydrantmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HydrantmapEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HydrantmapEurekaApplication.class, args);
	}
}
