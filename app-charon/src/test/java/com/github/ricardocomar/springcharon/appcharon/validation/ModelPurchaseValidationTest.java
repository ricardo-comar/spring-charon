package com.github.ricardocomar.springcharon.appcharon.validation;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ricardocomar.springcharon.appcharon.fixture.PurchaseModelFixture;
import com.github.ricardocomar.springcharon.appcharon.model.Purchase;
import com.github.ricardocomar.springcharon.appcharon.model.PurchaseItem;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ValidationSpringConfig.class)
public class ModelPurchaseValidationTest {

	@Autowired
	private ValidatorPurchase validator;
	private Purchase purchase;

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates(PurchaseModelFixture.class.getPackage().getName());
	}

	@Before
	public void before() {
		purchase = Fixture.from(Purchase.class).gimme("valid");
	}

	@Test
	public void testValid() {
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(true));
		assertThat(result.getErrors(), empty());
	}

	@Test
	public void testEmptyTransaction() throws Exception {
		purchase.setTransaction(null);
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}

	@Test
	public void testInvalidTransaction() throws Exception {
		purchase.setTransaction("ABC");
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}

	@Test
	public void testEmptyId() throws Exception {
		purchase.setId(null);
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}

	@Test
	public void testEmptyCustomer() throws Exception {
		purchase.setCustomer(null);
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}

	@Test
	public void testEmptyStatus() throws Exception {
		purchase.setStatus(null);
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}

	@Test
	public void testEmptyDate() throws Exception {
		purchase.setDate(null);
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}

	@Test
	public void testInvalidDate() throws Exception {
		purchase.setDate(LocalDateTime.now().plusDays(1));
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}

//	@Test
//	public void testNullItems() throws Exception {
//		purchase.setItems(null);
//		validator.validate(purchase);
//	}
//
//	@Test
//	public void testEmptyItems() throws Exception {
//		purchase.setItems(new ArrayList<PurchaseItem>());
//		validator.validate(purchase);
//	}
	@Test
	public void testEmptyItems() throws Exception {
		purchase.setItems(new ArrayList<PurchaseItem>());
		purchase.setTotalValue(BigDecimal.ZERO);
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(true));
		assertThat(result.getErrors(), empty());
	}

	@Test
	@Ignore("validação desativada")
	public void testOverloadedItems() throws Exception {
		purchase.setItems(Fixture.from(PurchaseItem.class).gimme(6, "g6play"));
		purchase.setTotalValue(
				purchase.getItems().stream().map(PurchaseItem::getValue).reduce(BigDecimal.ZERO, BigDecimal::add));
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}

	@Test
	public void testEmptyTotalValue() throws Exception {
		purchase.setTotalValue(null);
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}

	@Test
	public void testInvalidTotalValue() throws Exception {
		purchase.setTotalValue(new BigDecimal(1.0));
		final ValidationResult result = validator.validate(purchase);
		assertThat(result.isValid(), equalTo(false));
		assertThat(result.getErrors(), not(empty()));
	}
}
