package com.github.ricardocomar.springcharon.appmirror.config;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.RecordInterceptor;

public class KafkaConsumerConfig {

//	@Autowired
//	private AppProperties appProps;

	@Bean
	@Lazy
	public ConsumerFactory<String, Object> consumerFactory(@Autowired final KafkaProperties kafkaProps) {
//		final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
//		jsonDeserializer.addTrustedPackages("*");
//		return new DefaultKafkaConsumerFactory<>(kafkaProps.buildConsumerProperties(), new StringDeserializer(),
//				jsonDeserializer);

		return new DefaultKafkaConsumerFactory<>(kafkaProps.buildConsumerProperties());
	}

	@Bean
	@Lazy
	public ConcurrentKafkaListenerContainerFactory<String, GenericRecord> kafkaListenerContainerFactory(
			final ConsumerFactory<String, Object> consumerFactory) {
		final ConcurrentKafkaListenerContainerFactory<String, GenericRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setRecordInterceptor(recordInterceptor());
//		factory.setConcurrency(appProps.getConsumer().getContainerFactory().getConcurrency());
//		factory.getContainerProperties()
//				.setPollTimeout(appProps.getConsumer().getContainerFactory().getProperties().getPoolTimeout());
		return factory;
	}

	private RecordInterceptor<String, GenericRecord> recordInterceptor() {

		return new RecordInterceptor<String, GenericRecord>() {

			@Override
			public ConsumerRecord<String, GenericRecord> intercept(final ConsumerRecord<String, GenericRecord> record) {

				record.headers().headers(AppProperties.HEADER_CORRELATION_ID).forEach((h) -> {
					MDC.put(AppProperties.PROP_CORRELATION_ID, new String(h.value()));
				});
				return record;
			}
		};
	}
}
