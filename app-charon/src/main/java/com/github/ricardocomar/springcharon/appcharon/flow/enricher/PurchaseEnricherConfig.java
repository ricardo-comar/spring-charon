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
import org.springframework.kafka.support.KafkaHeaders;

@Configuration
public class PurchaseEnricherConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseEnricherConfig.class);

	@Bean
	@Transformer(inputChannel = "purchaseOutboundEnricherChannel", outputChannel = "kafkaTransformerChannel") // kafkaOutboundChannel
	public HeaderEnricher purchaseEnricher() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(KafkaHeaders.TOPIC, new StaticHeaderValueMessageProcessor<>("topic-sync-purchase"));
		headersMap.put(KafkaHeaders.MESSAGE_KEY,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("payload.syncKey", String.class));
		LOGGER.info("Message will be enriched with outcomming information: {}" + headersMap);

		return new HeaderEnricher(headersMap);
	}

}
