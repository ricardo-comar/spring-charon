package com.github.ricardocomar.springcharon.appmirror;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.github.ricardocomar.springcharon.appmirror.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MirrorApplication {

	public static void main(final String[] args) {
		SpringApplication.run(MirrorApplication.class);
	}

}
