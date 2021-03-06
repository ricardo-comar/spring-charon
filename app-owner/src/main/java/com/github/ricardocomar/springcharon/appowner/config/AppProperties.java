package com.github.ricardocomar.springcharon.appowner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "app-owner")
public class AppProperties {

	public static final String HEADER_SYNC_ID = "X-Sync-Id";
	public static final String HEADER_SYNC_DOMAIN = "X-Sync-Domain";
	public static final String HEADER_SYNC_SEQUENCE = "X-Sync-Sequence";
	public static final String HEADER_CHARON_TRANSACTION = "X-Charon-Transaction";
	public static final String HEADER_CORRELATION_ID = "X-Correlation-id";
	public static final String PROP_CORRELATION_ID = "correlationId";

	private String instanceId;
	
	private ConcurrentProcessor concurrentProcessor;

	@Data
	@NoArgsConstructor
	public static class ConcurrentProcessor {

		private Integer waitTimeout = 10000;
	}

	private Consumer consumer;

	@Data
	@NoArgsConstructor
	public static class Consumer {

		private ContainerFactory containerFactory;

		@Data
		@NoArgsConstructor
		public static class ContainerFactory {

			private Integer concurrency = 25;

			private Properties properties;

			@Data
			@NoArgsConstructor
			public static class Properties {

				private Integer poolTimeout = 30000;
			}

		}
	}

	private RestTemplate restTemplate;

	@Data
	@NoArgsConstructor
	public static class RestTemplate {

		private Redirect redirect;

		@Data
		@NoArgsConstructor
		public static class Redirect {

			private Integer readTimeout = 1000;
			
			private Integer connectTimeout = 500;
		}
	}

}
