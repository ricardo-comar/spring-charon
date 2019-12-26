package com.github.ricardocomar.springcharon.appowner.config;

import java.io.IOException;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
	
	@Autowired
	private AppProperties appProps;
	
	@Bean
	public UrlValidator urlValidator() {
		 final UrlValidator validator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES + UrlValidator.ALLOW_LOCAL_URLS);
		 return validator;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		final RestTemplate restTemplate = new RestTemplate(requestFactory());
		restTemplate.setErrorHandler(errorHandler());
		
		return restTemplate;
	}

	private ClientHttpRequestFactory requestFactory() {
		final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setReadTimeout(appProps.getRestTemplate().getRedirect().getReadTimeout());
		requestFactory.setConnectTimeout(appProps.getRestTemplate().getRedirect().getConnectTimeout());
		return requestFactory;
	}

	private ResponseErrorHandler errorHandler() {
		return new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(final ClientHttpResponse response) throws IOException {
				return response.getStatusCode().isError();
			}
			
			@Override
			public void handleError(final ClientHttpResponse response) throws IOException {
			}
		};
	}

}
