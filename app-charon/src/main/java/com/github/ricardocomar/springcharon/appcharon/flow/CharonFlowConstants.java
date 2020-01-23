package com.github.ricardocomar.springcharon.appcharon.flow;

public interface CharonFlowConstants {

	String FLOW_1_INBOUND_JMS_ENRICHER_CHANNEL = "jmsEnricherChannel";
	String FLOW_2_INBOUND_SEQ_ROUTER_CHANNEL = "jmsSequenceRouterChannel";
	String FLOW_3_INBOUND_UPDATE_SEQ_CHANNEL = "inboundUpdateSequenceChannel";
	String FLOW_4_INBOUND_VALIDATOR_CHANNEL = "inboundValidatorEnricherChannel";
	String FLOW_5_INBOUND_FILTER_CHANNEL = "inboundFilterChannel";

	String FLOW_SEQ_RETRY_CHANNEL = "inboundSequenceRetryChannel";
	String FLOW_SEQ_BREAKER_CHANNEL = "inboundSequenceBreakerChannel";
	String FLOW_DISCARD_CHANNEL = "discardChannel";
	String FLOW_OUTBOUND_SEQ_BREAK_NOTIFICATION = "outboundSequenceBreakNotificationChannel";

	String FLOW_6_MODEL_TRANSFORMER_CHANNEL = "modelTransformerChannel";
	String FLOW_7_MODEL_VALIDATION_CHANNEL = "modelValidatorEnricherChannel";
	String FLOW_8_MODEL_FILTER_CHANNEL = "modelFilterChannel";

	String FLOW_9_ROUTING_FLOW_CHANNEL = "flowRouterChannel";
	String FLOW_10_ROUTING_NOTIFICATION_CHANNEL = "flowRouterChannel";

	String FLOW_11_OUTBOUND_MIRROR_ENRICHER_CHANNEL = "outboundMirrorEnricherChannel";
	String FLOW_12_OUTBOUND_MIRROR_TRANSFORMER_CHANNEL = "outboundMirrorTransformerChannel";

	String FLOW_OUTBOUND_KAFKA_CHANNEL = "outboundKafkaChannel";
	String FLOW_OUTBOUND_JMS_RETRY_CHANNEL = "jmsRetryOutboundChannel";
}
