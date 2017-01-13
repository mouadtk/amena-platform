package com.opm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages = "com.opm")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class OpmDbAccessManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpmDbAccessManagerApplication.class, args);
	}
	
}
