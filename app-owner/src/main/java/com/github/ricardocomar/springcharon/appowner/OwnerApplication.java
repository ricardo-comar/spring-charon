package com.github.ricardocomar.springcharon.appowner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;

import com.github.ricardocomar.springcharon.appowner.config.AppProperties;

@EnableKafka
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class OwnerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(OwnerApplication.class, args);
	}

}
