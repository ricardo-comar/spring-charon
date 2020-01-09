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
public class MQEnricherConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MQEnricherConfig.class);

	@Bean
	@Transformer(inputChannel = "jmsPurchaseEnricherChannel", outputChannel = "jmsPurchaseSequenceChannel")
	public HeaderEnricher jmsPurchaseEnricher() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_SYNC_ID,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("headers['X-Sync-id']", String.class));
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_SYNC_SEQUENCE,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("headers['X-Sync-Sequence']", Integer.class));
		LOGGER.info("Message will be enriched with incomming type and validation result: {}", headersMap);

		return new HeaderEnricher(headersMap);
	}

}
