package com.nginxtelegrm.NginxTelegramMessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NginxTelegramMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(NginxTelegramMessageApplication.class, args);
	}

}
