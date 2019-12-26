package com.github.ricardocomar.springcharon.appcharon.processor;

import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ricardocomar.springcharon.appcharon.model.ConsumerModel;
import com.github.ricardocomar.springcharon.appcharon.producer.ReturnProducer;
import com.github.ricardocomar.springcharon.appcharon.transformer.AvroTransformer;
import com.github.ricardocomar.springcharon.appcharon.transformer.TrancodeTransformer;
import com.github.ricardocomar.springcharon.appcharon.validation.ModelValidator;

import br.com.fluentvalidator.context.ValidationResult;

@Component
public class MessageProcessor {

	@Autowired
	private TrancodeTransformer trancodeTransformer;

	@Autowired
	private AvroTransformer avroTransformer;

	@Autowired
	private ModelValidator validator;

	@Autowired
	private ReturnProducer producer;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

	public void process(final String message, final String requestId) {
		LOGGER.info("Processing message: {}", message);
		
		final ConsumerModel teamTrancode = trancodeTransformer.fromTrancode(message);
		LOGGER.info("Message transformed into bean: {}", teamTrancode);
		
		final ValidationResult validationResult = validator.validate(teamTrancode);
		LOGGER.info("Validation result: {}", validationResult);
		
		final GenericRecord avro = avroTransformer.from(teamTrancode);
		LOGGER.info("Bean transformed into response: {}", avro);

		LOGGER.info("Sending bean to response topic");
		producer.sendMessage(avro, requestId);
	}

}
