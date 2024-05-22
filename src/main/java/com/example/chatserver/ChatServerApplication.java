package com.example.chatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@ComponentScan({})
@EnableJpaRepositories("com.example.chatserver.repository")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ChatServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatServerApplication.class, args);
	}
}
