package com.github.ricardocomar.springcharon.appcharon.flow.outbound;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHandler;

import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;

@Configuration
public class MQOutboundConfig {


	@Bean
	@ServiceActivator(inputChannel = CharonFlowConstants.FLOW_OUTBOUND_JMS_RETRY_CHANNEL)
	public MessageHandler jmsMessageHandler(final JmsTemplate jmsTemplate) {
		final JmsSendingMessageHandler handler = new JmsSendingMessageHandler(jmsTemplate);
		handler.setDestinationName("sync.purchase.retry"); // TODO: Trocar por destinationExpression
		return handler;
	}

//	@Bean(destroyMethod = "destroy")
////	@InboundChannelAdapter(channel = CharonFlowConstants.FLOW_OUTBOUND_JMS_RETRY_CHANNEL)
//	public JmsChannelFactoryBean jmsRetryOutboundChannel(final ConnectionFactory jmsConnFactory) {
//		final JmsChannelFactoryBean factory = new JmsChannelFactoryBean(true);
//		factory.setMessageConverter(new MessagingMessageConverter());
//		factory.setConnectionFactory(jmsConnFactory);
//		factory.setSessionTransacted(true);
//		factory.setDestinationName("sync.purchase.retry"); // TODO: Trocar por destinationResolver e headers
//		return factory;
//	}

}
