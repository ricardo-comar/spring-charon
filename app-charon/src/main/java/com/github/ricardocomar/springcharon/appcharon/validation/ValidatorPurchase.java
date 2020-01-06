package com.github.ricardocomar.springcharon.appcharon.validation;

import static br.com.fluentvalidator.predicate.CollectionPredicate.empty;
import static br.com.fluentvalidator.predicate.ComparablePredicate.lessThanOrEqual;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.StringPredicate.stringMatches;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ricardocomar.springcharon.appcharon.model.Purchase;
import com.github.ricardocomar.springcharon.appcharon.model.PurchaseItem;

import br.com.fluentvalidator.AbstractValidator;

@Component
public class ValidatorPurchase extends AbstractValidator<Purchase> {

	@Autowired
	private ValidatorPurchaseItem valItem;

	@Override
	public void rules() {

		ruleFor("transaction", Purchase::getTransaction).must(stringMatches("TRANPURC-1"))
				.withMessage("transaction must be equal to TRANPURC-1");

		ruleFor("id", Purchase::getId).must(not(stringEmptyOrNull())).withMessage("id is mandatory");

		ruleFor("customer", Purchase::getCustomer).must(not(stringEmptyOrNull())).withMessage("customer is mandatory");

		ruleFor("status", Purchase::getStatus).must(not(nullValue())).withMessage("status is mandatory");

		ruleFor("date", Purchase::getDate).must(not(nullValue())).withMessage("date is mandatory")
				.must(lessThanOrEqual(LocalDateTime.now())).withMessage("date must be in the past");

//		ruleFor("items", Purchase::getItems).must(between(Collection::size, 0, 6))
//				.withMessage("items is mandatory and must be 1-5 size");
		ruleForEach("items", Purchase::getItems).whenever(not(empty())).withValidator(valItem);

		ruleFor("totalValue", Purchase::getTotalValue).must(not(nullValue())).withMessage("totalValue is mandatory");
		ruleFor("totalValue", purchase -> purchase).must(p -> 0 == p.getTotalValue().compareTo( //
				p.getItems().stream().map(PurchaseItem::getValue) //
						.reduce(BigDecimal.ZERO, BigDecimal::add)))
				.withMessage("totalValue must be the sum of items values");
	}

}
