package com.shopping.electroshopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ComponentScan(basePackages = "com.shopping.electroshopping")
@EnableConfigurationProperties
public class ElectroshoppingApplicationProject {


	public static void main(String[] args) {
		SpringApplication.run(ElectroshoppingApplicationProject.class, args);
	}

}
