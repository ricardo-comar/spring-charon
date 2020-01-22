package com.github.ricardocomar.springcharon.appcharon.flow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.model.ConsumerModel;

@Configuration
public class DiscardFilterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscardFilterConfig.class);

	@Filter(inputChannel = "jmsRetryDiscardChannel", outputChannel = "discardChannel")
	public boolean jmsRetryDiscardChannel(final Message<String> msg) {

		LOGGER.warn("Too many retries, will be discarded: {}", msg);
		return true;
	}

	@Filter(inputChannel = "messageDiscardChannel", outputChannel = "discardChannel")
	public boolean jmsMessageDiscardChannel(final Message<String> msg) {

		LOGGER.warn("Message will be discarded: {}", msg);
		return true;
	}

	@Filter(inputChannel = "modelDiscardChannel", outputChannel = "discardChannel")
	public boolean modelDiscardChannel(final Message<ConsumerModel> msg) {

		LOGGER.warn("Model will be discarded: {}", msg);
		return true;
	}

	@Bean
	@ServiceActivator(inputChannel = "discardChannel")
	public LoggingHandler discardChannelBean() {
		final LoggingHandler adapter = new LoggingHandler(LoggingHandler.Level.WARN);
		adapter.setLoggerName("CHARON_LOGGER");
		adapter.setLogExpressionString(
				"headers['" + SpringIntegrationConfig.X_MSG_HEADER_SYNC_ID
						+ "'] + ': will be discarded - payload:' + payload");
		return adapter;
	}

}
