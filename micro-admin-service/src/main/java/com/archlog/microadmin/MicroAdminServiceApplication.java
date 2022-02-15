package com.archlog.microadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroAdminServiceApplication.class, args);
	}

}
