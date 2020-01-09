package com.github.ricardocomar.springcharon.appcharon.validation;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

import org.springframework.stereotype.Component;

import br.com.fluentvalidator.AbstractValidator;

@Component
public class ValidatorMQPurchase extends AbstractValidator<String> {
	
	@Override
	public void rules() {

		ruleFor("payload", msg -> msg) //
				.must(not(stringEmptyOrNull())).withMessage("payload is mandatory") //
//				.must(stringSize(292)).withMessage("invalid size, must be 292 length, not ")
		;

	}

}
