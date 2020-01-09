package com.github.ricardocomar.springcharon.appcharon.flow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.DelayHandler;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.google.common.base.Optional;

@Configuration
public class MQRetryFilterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MQRetryFilterConfig.class);

	private static final Integer MAX_RETRY_ATTEMPTS = 2;

	@Filter(inputChannel = "jmsPurchaseSequenceChannel", outputChannel = "inboundPurchaseChannel", discardChannel = "jmsRetryDelayChannel")
	public boolean jmsSequenceChannel(final Message<String> msg) {

		LOGGER.info("Message sequence will be verified: {}", msg);
		return false;
	}

	@Bean
	@ServiceActivator(inputChannel = "jmsRetryDelayChannel")
	public DelayHandler delayer() {
		final DelayHandler handler = new DelayHandler("delayer.messageGroupId");
		handler.setDefaultDelay(5000L);
		handler.setDelayExpressionString("headers['delay']");
		handler.setOutputChannelName("jmsRetryChannel");
		return handler;
	}

	@Filter(inputChannel = "jmsRetryChannel", outputChannel = "jmsRetryEnricherChannel", discardChannel = "discardChannel")
	public boolean jmsRetryChannel(final Message<String> msg) {

		LOGGER.info("Message out of sequence: {}", msg);

		final Integer retryCount = (Integer) msg.getHeaders()
				.get(SpringIntegrationConfig.X_MSG_HEADER_SYNC_RETRY_COUNT);

		if (Optional.fromNullable(retryCount).or(0) < MAX_RETRY_ATTEMPTS) {

			LOGGER.info("Message will be sent to retry queue");
			return true;
		}

		LOGGER.warn("Message attempts above permited: ", retryCount);
		return false;
	}

}
