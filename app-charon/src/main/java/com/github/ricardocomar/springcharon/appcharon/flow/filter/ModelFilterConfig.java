package com.github.ricardocomar.springcharon.appcharon.flow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.model.ConsumerModel;

import br.com.fluentvalidator.context.ValidationResult;

@Configuration
public class ModelFilterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelFilterConfig.class);

	@Filter(inputChannel = "modelFilterChannel", outputChannel = "modelRouterChannel", discardChannel = "modelDiscardChannel")
	public boolean modelFilterChannel(final Message<ConsumerModel> msg) {

		final ValidationResult validationResult = (ValidationResult) msg.getHeaders()
				.get(SpringIntegrationConfig.X_MSG_HEADER_MODEL_VALIDATION);
		LOGGER.info("Model validation will be checked: {}", validationResult);

		return validationResult.isValid();
	}

}
