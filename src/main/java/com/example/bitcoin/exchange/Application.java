package com.example.bitcoin.exchange;

import java.util.Arrays;

import com.example.bitcoin.exchange.service.CommandLineService;
import com.example.bitcoin.exchange.service.impl.CommandLineServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application implements ApplicationRunner {
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		if (!Arrays.asList(env.getActiveProfiles()).contains("test")) {
			CommandLineService commandLineService = new CommandLineServiceImpl();
			commandLineService.run();
		}
	}
}
