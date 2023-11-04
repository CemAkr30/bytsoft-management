package com.codeforinca.bytsoftapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
	scanBasePackages = {
		"com.codeforinca.bytsoftapi",
		"com.codeforinca.bytsoftcore"
	}
)
@EnableJpaRepositories(
	basePackages = {
		"com.codeforinca.bytsoftapi",
		"com.codeforinca.bytsoftcore"
	}
)
@EntityScan(
	basePackages = {
		"com.codeforinca.bytsoftapi",
		"com.codeforinca.bytsoftcore"
	}
)
@ComponentScan(
	basePackages = {
		"com.codeforinca.bytsoftapi",
		"com.codeforinca.bytsoftcore"
	}
)
public class BytsoftApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BytsoftApiApplication.class, args);
	}

}
