package com.github.ricardocomar.springcharon.etlconsumer.transformer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ricardocomar.springcharon.appcharon.model.ConsumerModel;
import com.github.ricardocomar.springcharon.appcharon.model.Purchase;
import com.github.ricardocomar.springcharon.appcharon.transformer.TrancodeTransformer;
import com.github.ricardocomar.springcharon.etlconsumer.fixture.PurchaseModelFixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TransformerSpringConfig.class)
public class TrancodeTransformerTest {

	@Autowired
	private TrancodeTransformer transformer;

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates(PurchaseModelFixture.class.getPackage().getName());
	}

	@Test
	public void testValidPurchase() throws Exception {

		final Purchase purchase = Fixture.from(Purchase.class).gimme("valid");

		final String trancode = transformer.to(purchase);
		assertThat(trancode, not(isEmptyOrNullString()));

		final ConsumerModel newModel = transformer.fromTrancode(trancode);

		assertThat(newModel, equalTo(purchase));
	}
}
