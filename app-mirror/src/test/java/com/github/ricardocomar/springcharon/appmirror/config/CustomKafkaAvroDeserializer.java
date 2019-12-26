package com.github.ricardocomar.springcharon.appmirror.config;

import java.io.IOException;
import java.util.Map;

import org.apache.avro.Schema;

import com.github.ricardocomar.springbootetl.model.PurchaseAvro;
import com.github.ricardocomar.springbootetl.model.TeamAvro;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

public class CustomKafkaAvroDeserializer extends KafkaAvroDeserializer {

	@Override
	public void configure(final Map<String, ?> configs, final boolean isKey) {
		super.schemaRegistry = new CustomSchemaRegistryClient(configs);
		super.configure(configs, isKey);
	} 

}
