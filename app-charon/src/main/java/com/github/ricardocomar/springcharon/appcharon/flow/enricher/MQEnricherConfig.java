package com.github.ricardocomar.springcharon.appcharon.flow.enricher;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;

@Configuration
public class MQEnricherConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MQEnricherConfig.class);

	@Bean
	@Transformer(inputChannel = "jmsPurchaseEnricherChannel", outputChannel = "jmsTransformerChannel")
	public HeaderEnricher jmsPurchaseEnricher() {

		LOGGER.info("Message will be enriched to incomming message type");

		return new HeaderEnricher(Collections.singletonMap(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_TYPE,
				new StaticHeaderValueMessageProcessor<>("TRANPURC-1")));
	}

}
