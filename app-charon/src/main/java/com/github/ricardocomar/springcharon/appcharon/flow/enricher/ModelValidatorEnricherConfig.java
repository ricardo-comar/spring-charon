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
public class ModelValidatorEnricherConfig {

	@Bean
	@Transformer(inputChannel = CharonFlowConstants.FLOW_7_MODEL_VALIDATION_CHANNEL, outputChannel = CharonFlowConstants.FLOW_8_MODEL_FILTER_CHANNEL)
	public HeaderEnricher modelValidatorEnricher() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_MODEL_VALIDATION,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("@modelValidator.validate(payload)",
						ValidationResult.class));

		return new HeaderEnricher(headersMap);
	}

}
