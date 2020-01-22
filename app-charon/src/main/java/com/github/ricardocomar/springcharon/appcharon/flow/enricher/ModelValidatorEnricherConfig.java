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

import br.com.fluentvalidator.context.ValidationResult;

@Configuration
public class ModelEnricherConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelEnricherConfig.class);

	@Bean
	@Transformer(inputChannel = "modelEnricherChannel", outputChannel = "modelFilterChannel")
	public HeaderEnricher modelEnricher() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_MODEL_VALIDATION,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("@modelValidator.validate(payload)",
						ValidationResult.class));
		LOGGER.info("Message will be enriched with model validation result: {}", headersMap);

		return new HeaderEnricher(headersMap);
	}

}
