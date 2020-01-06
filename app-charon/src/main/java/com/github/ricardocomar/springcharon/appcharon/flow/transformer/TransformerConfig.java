package com.github.ricardocomar.springcharon.appcharon.flow.transformer;

import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.model.ConsumerModel;
import com.github.ricardocomar.springcharon.appcharon.transformer.AvroTransformer;
import com.github.ricardocomar.springcharon.appcharon.transformer.TrancodeTransformer;

@Configuration
public class TransformerConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransformerConfig.class);

	@Autowired
	private TrancodeTransformer trancodeTransformer;

	@Autowired 
	private AvroTransformer avroTransformer;

	@Transformer(inputChannel = "jmsTransformerChannel", outputChannel = "modelFilterChannel")
	public ConsumerModel transformStringToModel(final Message<String> msg,
			@Header(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_TYPE) final String transaction) {

		LOGGER.info("Message from type {} will be transformed to model: {}", transaction, msg);
		final ConsumerModel model = trancodeTransformer.fromTrancode(transaction, msg.getPayload());

		return model;
	}

	@Transformer(inputChannel = "purchaseTransformerChannel", outputChannel = "purchaseEnricherChannel")
	public GenericRecord transformModelToAvro(final ConsumerModel model) {

		LOGGER.info("Message from type \"{}\" will be transformed to Avro", model.getClass().getSimpleName());
		final GenericRecord avro = avroTransformer.from(model);

		return avro;
	}

}
