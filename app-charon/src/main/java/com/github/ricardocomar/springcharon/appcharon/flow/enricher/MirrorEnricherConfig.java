package com.github.ricardocomar.springcharon.appcharon.flow.enricher;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.kafka.support.KafkaHeaders;

import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;

@Configuration
public class MirrorEnricherConfig {

	@Bean
	@Transformer(inputChannel = CharonFlowConstants.FLOW_11_OUTBOUND_MIRROR_ENRICHER_CHANNEL, outputChannel = CharonFlowConstants.FLOW_12_OUTBOUND_MIRROR_TRANSFORMER_CHANNEL)
	public HeaderEnricher purchaseEnricher() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(KafkaHeaders.TOPIC, new StaticHeaderValueMessageProcessor<>("topic-sync-purchase"));
		headersMap.put(KafkaHeaders.MESSAGE_KEY,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("payload.syncKey", String.class));

		return new HeaderEnricher(headersMap);
	}

}
