package com.github.ricardocomar.springcharon.appcharon.flow.enricher;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;

@Configuration
public class MQRetryEnricherConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MQRetryEnricherConfig.class);

	@Bean
	@Transformer(inputChannel = "jmsRetryEnricherChannel", outputChannel = "jmsRetryOutboundChannel")
	public HeaderEnricher jmsRetryEnricher() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_SYNC_RETRY_COUNT,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>(
						"headers['" + SpringIntegrationConfig.X_MSG_HEADER_SYNC_RETRY_COUNT + "']?:0 + 1",
						Integer.class));
		LOGGER.info("Message will be enriched with incomming type and validation result: {}", headersMap);

		return new HeaderEnricher(headersMap);
	}

}
