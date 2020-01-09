package com.github.ricardocomar.springcharon.appcharon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.ricardocomar.springcharon.appcharon.CharonApplication;

@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackageClasses = CharonApplication.class)
@EnableTransactionManagement
public class SpringIntegrationConfig {

	public static final String X_MSG_HEADER_SYNC_ID = "X-Charon-Sync-id";
	public static final String X_MSG_HEADER_SYNC_SEQUENCE = "X-Charon-Sync-Sequence";
	public static final String X_MSG_HEADER_SYNC_RETRY_COUNT = "X-Charon-Sync-Retry-Count";

	public static final String X_MSG_HEADER_INBOUND_TYPE = "X-Charon-Inbound-Type";
	public static final String X_MSG_HEADER_INBOUND_VALIDATION = "X-Charon-Inbound-Validation";

	public static final String X_MSG_HEADER_MODEL_VALIDATION = "X-Charon-Model-Validation";

	public static final String X_MSG_HEADER_OUTBOUND_TYPE = "X-Charon-Outbound-Type";

	@Bean
	@ServiceActivator(inputChannel = "loggingChannel")
	public LoggingHandler loggingChannelBean() {
		final LoggingHandler adapter = new LoggingHandler(LoggingHandler.Level.INFO);
		adapter.setLoggerName("CHARON_LOGGER");
		adapter.setLogExpressionString("headers['" + X_MSG_HEADER_SYNC_ID + "'] + ': ' + payload");
		return adapter;
	}

}
