package com.github.ricardocomar.springcharon.appcharon.validation;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import br.com.fluentvalidator.AbstractValidator;

@Component
public class ValidatorMQPurchase extends AbstractValidator<Message<String>> {
	
	@Override
	public void rules() {

		ruleFor("payload", msg -> msg.getPayload()) //
				.must(not(stringEmptyOrNull())).withMessage("payload is mandatory") //
//				.must(stringSize(292)).withMessage("invalid size, must be 292 length, not ")
		;

	}

}
