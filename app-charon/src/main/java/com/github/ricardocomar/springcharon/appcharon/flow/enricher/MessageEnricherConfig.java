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
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;

import br.com.fluentvalidator.context.ValidationResult;

@Configuration
public class MessageEnricherConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageEnricherConfig.class);

	@Bean
	@Transformer(inputChannel = "inboundPurchaseChannel", outputChannel = "inboundPurchaseFilterChannel")
	public HeaderEnricher inboundPurchaseEnricher() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_TYPE,
				new StaticHeaderValueMessageProcessor<>("TRANPURC-1"));
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_VALIDATION,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("@validatorMQPurchase.validate(payload)",
						ValidationResult.class));
		LOGGER.info("Message will be enriched with incomming type and validation result: {}", headersMap);

		return new HeaderEnricher(headersMap);
	}

}
