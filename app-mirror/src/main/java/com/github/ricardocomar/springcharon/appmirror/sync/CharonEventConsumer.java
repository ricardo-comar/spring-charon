package com.github.ricardocomar.springcharon.appmirror.sync;

import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.github.ricardocomar.springcharon.appmirror.config.AppProperties;
import com.github.ricardocomar.springcharon.model.PurchaseAvro;
import com.github.ricardocomar.springcharon.model.SyncAckAvro;

@Component
public class CharonEventConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CharonEventConsumer.class);

	@Autowired
	private KafkaTemplate<String, GenericRecord> kafkaTemplate;

	@KafkaListener(topics = "topic-sync-purchase")
	public void consumeResponse(@Payload final GenericRecord record,
			@Header(required = false, name = AppProperties.HEADER_SYNC_ID) final String syncId)
			throws Exception {

		LOGGER.info("Received message with id {}", syncId);
		final PurchaseAvro purchase = (PurchaseAvro) record;
		LOGGER.info("Received purchase: {}", purchase);
		
		final SyncAckAvro ack = SyncAckAvro.newBuilder().setId(syncId).setSequence(purchase.getSyncSequence())
				.setTransaction(purchase.getSyncTransaction()).build();
		kafkaTemplate.send("topic-sync-ack", purchase.getSyncTransaction(), ack);

	}
}
