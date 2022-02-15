package com.archlog.microrfid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroRfidServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroRfidServiceApplication.class, args);
	}

}
