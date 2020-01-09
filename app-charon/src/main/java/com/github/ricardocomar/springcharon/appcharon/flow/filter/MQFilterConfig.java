package com.github.ricardocomar.springcharon.appcharon.flow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;

import br.com.fluentvalidator.context.ValidationResult;

@Configuration
public class MQFilterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MQFilterConfig.class);

	@Filter(inputChannel = "inboundPurchaseFilterChannel", outputChannel = "modelTransformerChannel", discardChannel = "messageDiscardChannel")
	public boolean jmsPurchaseFilterChannel(final Message<String> msg) {

		final ValidationResult validationResult = (ValidationResult) msg.getHeaders()
				.get(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_VALIDATION);
		LOGGER.info("Message validation will be checked: {}", validationResult);

		return validationResult.isValid();
	}

}
