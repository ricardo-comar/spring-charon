package com.github.ricardocomar.springcharon.appcharon.flow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;

import br.com.fluentvalidator.context.ValidationResult;

@Configuration
public class MessageFilterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageFilterConfig.class);

	@Filter(inputChannel = CharonFlowConstants.FLOW_5_INBOUND_FILTER_CHANNEL, outputChannel = CharonFlowConstants.FLOW_6_MODEL_TRANSFORMER_CHANNEL, discardChannel = CharonFlowConstants.FLOW_SEQ_BREAKER_CHANNEL)
	public boolean inboundFilterFilter(final Message<String> msg) {

		final ValidationResult validationResult = (ValidationResult) msg.getHeaders()
				.get(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_VALIDATION);
		LOGGER.info("Message validation will be checked: {}", validationResult);

		return validationResult.isValid();
	}

}
