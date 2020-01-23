package com.github.ricardocomar.springcharon.appcharon.flow.outbound;

import org.apache.avro.generic.GenericRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHandler;

import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;

@Configuration
public class KafkaOutboundConfig {

	@Bean
	@ServiceActivator(inputChannel = CharonFlowConstants.FLOW_OUTBOUND_KAFKA_CHANNEL)
	public MessageHandler kafkaMessageHandler(final KafkaTemplate<String, GenericRecord> kafkaTemplate) {
		final KafkaProducerMessageHandler<String, GenericRecord> handler = new KafkaProducerMessageHandler<>(
				kafkaTemplate);
		return handler;
	}

}
