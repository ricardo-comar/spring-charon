package com.github.ricardocomar.springcharon.appowner.entrypoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.ricardocomar.springcharon.appowner.entrypoint.model.PurchaseItemRequest;
import com.github.ricardocomar.springcharon.appowner.entrypoint.model.PurchaseRequest;
import com.github.ricardocomar.springcharon.appowner.service.PurchaseProcessor;

@RestController
public class ProcessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);

	@Autowired
	private PurchaseProcessor processor;

	@PostMapping(value = "/purchase")
	@ResponseBody
	public ResponseEntity<?> process(@RequestBody final PurchaseRequest request) {

		LOGGER.info("Purchase received: {}", request);

		try {
			processor.handle(request);
			return ResponseEntity.noContent().build();

		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{ \"error\": \"" + e.getMessage() + "\"");
		}

	}

	@PostMapping(value = "/purchase/{id}/item")
	@ResponseBody
	public ResponseEntity<?> process(@PathVariable(name = "id", required = true) final Long purchaseId,
			@RequestBody final PurchaseItemRequest request) {

		LOGGER.info("Item received for purchase {}: {}", purchaseId, request);

		try {
			processor.handle(request);
			return ResponseEntity.noContent().build();

		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{ \"error\": \"" + e.getMessage() + "\"");
		}

	}

}
