package com.github.ricardocomar.springcharon.appcharon.processor;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.apache.avro.generic.GenericRecord;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ricardocomar.springbootetl.model.PurchaseAvro;
import com.github.ricardocomar.springcharon.appcharon.consumer.model.RequestMessage;
import com.github.ricardocomar.springcharon.appcharon.fixture.RequestMessageFixture;
import com.github.ricardocomar.springcharon.appcharon.mapper.MapperSpringConfig;
import com.github.ricardocomar.springcharon.appcharon.producer.ReturnProducer;
import com.github.ricardocomar.springcharon.appcharon.transformer.TransformerSpringConfig;
import com.github.ricardocomar.springcharon.appcharon.validation.ValidationSpringConfig;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MessageProcessor.class, ValidationSpringConfig.class, TransformerSpringConfig.class,
		MapperSpringConfig.class })
public class MessageProcessorTest {

	@Autowired
	private MessageProcessor processor;

	@MockBean
	private ReturnProducer mockProducer;

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates(RequestMessageFixture.class.getPackage().getName());
	}

	@Test
	public void testValid() throws Exception {

		final String requestId = UUID.randomUUID().toString();
		final PurchaseAvro purchase = Fixture.from(PurchaseAvro.class).gimme("valid");

		Mockito.doAnswer((Answer<?>) invocation -> {
			assertEquals(purchase, invocation.getArgument(0));
			assertEquals(requestId, invocation.getArgument(1));
			return null;
		}).when(mockProducer).sendMessage(Mockito.any(GenericRecord.class), Mockito.anyString());

		final RequestMessage requestMessage = Fixture.from(RequestMessage.class).gimme("purchase");
		processor.process(requestMessage.getTrancode(), requestId);

		Mockito.verify(mockProducer, Mockito.atLeastOnce()).sendMessage(Mockito.any(GenericRecord.class),
				Mockito.anyString());

	}
}
