package com.github.ricardocomar.springcharon.appcharon.flow.inbound;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MessagingMessageConverter;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;

@Configuration
public class MQInboundConfig {

	@Bean
	public JmsMessageDrivenEndpoint jmsPurchaseInboundChannel(final ConnectionFactory jmsConnectionFactory,
			final JmsTransactionManager jmsTransactionManager) {
		return new JmsMessageDrivenEndpoint(container(jmsConnectionFactory, "sync.purchase", jmsTransactionManager),
				listener(CharonFlowConstants.FLOW_1_INBOUND_JMS_ENRICHER_CHANNEL));
	}

	@Bean
	public JmsMessageDrivenEndpoint jmsPurchaseRetryInboundChannel(final ConnectionFactory jmsConnectionFactory,
			final JmsTransactionManager jmsTransactionManager) {
		return new JmsMessageDrivenEndpoint(container(jmsConnectionFactory, "sync.purchase.retry", jmsTransactionManager),
				listener(CharonFlowConstants.FLOW_1_INBOUND_JMS_ENRICHER_CHANNEL));
	}

	@Bean
	public JmsTransactionManager jmsTransactionManager(final ConnectionFactory connectionFactory) {
		final JmsTransactionManager tm = new JmsTransactionManager();
		tm.setConnectionFactory(connectionFactory);
		return tm;
	}

	private DefaultMessageListenerContainer container(final ConnectionFactory jmsConnectionFactory,
			final String destination, final PlatformTransactionManager transactionManager) {
		final DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setMessageConverter(new MessagingMessageConverter());
		container.setTransactionManager(transactionManager);
		container.setSessionTransacted(true);
		container.setConnectionFactory(jmsConnectionFactory);
		container.setDestinationName(destination);
		return container;
	}

	private ChannelPublishingJmsMessageListener listener(final String channelName) {
		final ChannelPublishingJmsMessageListener listener = new ChannelPublishingJmsMessageListener();
		listener.setRequestChannelName(channelName);
		return listener;
	}
//
//	@Bean(destroyMethod = "destroy")
//	public JmsChannelFactoryBean jmsSyncPurchaseInboundChannel(final ConnectionFactory jmsConnFactory) {
//		final JmsChannelFactoryBean factory = new JmsChannelFactoryBean(true);
//		factory.setMessageConverter(new MessagingMessageConverter());
//		factory.setConnectionFactory(jmsConnFactory);
//		factory.setSessionTransacted(true);
//		factory.setDestinationName("sync.purchase");
//		return factory;
//	}
//
//	@Bean
//	@ServiceActivator(inputChannel = "jmsSyncPurchaseInboundChannel")
//	public BridgeHandler jmsSyncPurchaseInboundChannelBridge() {
//		final BridgeHandler bridge = new BridgeHandler();
//		bridge.setOutputChannelName("jmsPurchaseInboundChannel");
//		return bridge;
//	}
//
//	@Bean(destroyMethod = "destroy")
//	public JmsChannelFactoryBean jmsSyncPurchaseRetryInboundChannel(final ConnectionFactory jmsConnFactory) {
//		final JmsChannelFactoryBean factory = new JmsChannelFactoryBean(true);
//		factory.setMessageConverter(new MessagingMessageConverter());
//		factory.setConnectionFactory(jmsConnFactory);
//		factory.setSessionTransacted(true);
//		factory.setDestinationName("sync.purchase.retry");
//		return factory;
//	}
//
//	@Bean
//	@ServiceActivator(inputChannel = "jmsSyncPurchaseRetryInboundChannel")
//	public BridgeHandler jmsSyncPurchaseRetryInboundChannelBridge() {
//		final BridgeHandler bridge = new BridgeHandler();
//		bridge.setOutputChannelName("jmsPurchaseInboundChannel");
//		return bridge;
//	}
}
