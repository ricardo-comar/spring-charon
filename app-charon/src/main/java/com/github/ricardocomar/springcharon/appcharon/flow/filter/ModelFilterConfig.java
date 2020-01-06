package com.github.ricardocomar.springcharon.appcharon.flow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.model.ConsumerModel;
import com.github.ricardocomar.springcharon.appcharon.model.Purchase;
import com.github.ricardocomar.springcharon.appcharon.validation.ModelValidator;

import br.com.fluentvalidator.context.ValidationResult;

@Configuration
public class ModelFilterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelFilterConfig.class);

	@Autowired
	private ModelValidator validator;

	@Filter(inputChannel = "modelFilterChannel", outputChannel = "modelRouterChannel", discardChannel = "modelDiscardChannel")
	public boolean filterModelMessage(final Message<ConsumerModel> msg) {

		LOGGER.info("Message will be checked: {}", msg);

		final ValidationResult validationResult = validator.validate(msg.getPayload());
		LOGGER.info("Validation result: {}", validationResult);

		return validationResult.isValid();
	}

	@ServiceActivator(inputChannel = "modelDiscardChannel")
	public void discardPurchaseMessage(final Message<Purchase> msg) {

		LOGGER.warn("Model message will be discarded: {}", msg);
	}
}
