package com.github.ricardocomar.springcharon.appcharon.flow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.validation.ValidatorMQPurchase;

import br.com.fluentvalidator.context.ValidationResult;

@Configuration
public class MQFilterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MQFilterConfig.class);

	@Autowired
	private ValidatorMQPurchase validator;

	@Filter(inputChannel = "jmsPurchaseInboundChannel", outputChannel = "jmsPurchaseEnricherChannel", discardChannel = "jmsPurchaseDiscardChannel")
	public boolean filterMQMessage(final Message<String> msg) {

		LOGGER.info("Message will be checked: {}", msg);

		final ValidationResult validationResult = validator.validate(msg);
		LOGGER.info("Validation result: {}", validationResult);

		return validationResult.isValid();
	}

	@ServiceActivator(inputChannel = "jmsPurchaseDiscardChannel")
	public void discardMQMessage(final Message<String> msg) {

		LOGGER.warn("Message will be discarded: {}", msg);
	}
}
