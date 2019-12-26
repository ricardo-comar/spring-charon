package com.github.ricardocomar.springcharon.appcharon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.github.ricardocomar.springcharon.appcharon.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CharonApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CharonApplication.class);
	}

}
