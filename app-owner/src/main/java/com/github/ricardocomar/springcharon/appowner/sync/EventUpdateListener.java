package com.github.ricardocomar.springcharon.appowner.sync;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import com.github.ricardocomar.springcharon.appowner.config.AppProperties;
import com.github.ricardocomar.springcharon.appowner.sync.model.ModelUpdateEvent;

@Service
public class EventUpdateListener {

	@Autowired
	private JmsTemplate jmsTemplate;

	@EventListener
	public void notifyUpdate(final ModelUpdateEvent model) {
		final String syncId = UUID.randomUUID().toString();

		jmsTemplate.convertAndSend("queue.outbound", model.getPayload(), new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(final Message message) throws JMSException {
				message.setStringProperty(AppProperties.HEADER_SYNC_ID, syncId);
				return message;
			}
		});

	}
}
