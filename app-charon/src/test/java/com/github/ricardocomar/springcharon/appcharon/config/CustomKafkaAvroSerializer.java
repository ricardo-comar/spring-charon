package com.github.ricardocomar.springcharon.appcharon.config;

import java.util.Map;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class CustomKafkaAvroSerializer extends KafkaAvroSerializer {

	@Override
	public void configure(final Map<String, ?> configs, final boolean isKey) {
		super.schemaRegistry = new CustomSchemaRegistryClient(configs);
		super.configure(configs, isKey);
	} 

}