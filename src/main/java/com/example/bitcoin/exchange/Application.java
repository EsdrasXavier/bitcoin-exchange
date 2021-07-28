package com.example.bitcoin.exchange;

import com.example.bitcoin.exchange.service.CoinDeskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	@Autowired
	private CoinDeskService coinDeskService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public void test() {
		System.out.println("Result: " + coinDeskService.getCurrentBitcoinRateInCurrency("EUR"));
	}

}
