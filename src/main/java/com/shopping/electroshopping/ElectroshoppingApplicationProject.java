package com.shopping.electroshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.shopping.electroshopping")
@EnableConfigurationProperties
public class ElectroshoppingApplicationProject {

	public static void main(String[] args) {
		SpringApplication.run(ElectroshoppingApplicationProject.class, args);
	}

}
