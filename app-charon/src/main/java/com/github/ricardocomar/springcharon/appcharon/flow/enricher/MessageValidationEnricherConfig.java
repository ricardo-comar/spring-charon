package com.github.ricardocomar.springcharon.appcharon.flow.enricher;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;

import br.com.fluentvalidator.context.ValidationResult;

@Configuration
public class MessageValidationEnricherConfig {

	@Bean
	@Transformer(inputChannel = CharonFlowConstants.FLOW_4_INBOUND_VALIDATOR_CHANNEL, outputChannel = CharonFlowConstants.FLOW_5_INBOUND_FILTER_CHANNEL)
	public HeaderEnricher inboundPurchaseEnricher() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_VALIDATION,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("@validatorMQPurchase.validate(payload)",
						ValidationResult.class));

		return new HeaderEnricher(headersMap);
	}

}
