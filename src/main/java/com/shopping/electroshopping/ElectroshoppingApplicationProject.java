package com.shopping.electroshopping;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties
public class ElectroshoppingApplicationProject {

	public static void main(String[] args) {
		SpringApplication.run(ElectroshoppingApplicationProject.class, args);
	}

}
