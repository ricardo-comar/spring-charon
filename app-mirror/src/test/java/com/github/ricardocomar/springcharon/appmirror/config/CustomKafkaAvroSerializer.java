package com.github.ricardocomar.springcharon.appmirror.config;

import java.util.Map;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class CustomKafkaAvroSerializer extends KafkaAvroSerializer {

	@Override
	public void configure(final Map<String, ?> configs, final boolean isKey) {
		super.schemaRegistry = new CustomSchemaRegistryClient(configs);
		super.configure(configs, isKey);
	} 

}