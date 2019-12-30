package com.github.ricardocomar.springcharon.appowner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.github.ricardocomar.springcharon.appowner.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class OwnerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(OwnerApplication.class, args);
	}

}
