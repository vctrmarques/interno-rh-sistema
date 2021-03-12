package com.rhlinkcon;

import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@EnableScheduling
public class RhServerApplication {

	@PostConstruct
	public void init() {

		// It will set UTC timezone
		TimeZone.setDefault(TimeZone.getTimeZone("America/Recife"));

		// It will print UTC
		System.out.println("Spring boot application running in UTC timezone :" + new Date());

		// Herdando o contexto de segurança da thread local para novas threads.
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

	}

	void setGlobalSecurityContext() {

	}

	public static void main(String[] args) {
		SpringApplication.run(RhServerApplication.class, args);
	}

	@Bean
	public ExecutorService getExecutorService() {
		return Executors.newFixedThreadPool(2);
	}
}
