package com.github.ricardocomar.springcharon.appowner.entrypoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.ricardocomar.springcharon.appowner.entrypoint.model.ProcessRequest;
import com.github.ricardocomar.springcharon.appowner.service.ConcurrentProcessor;

@RestController
public class ProcessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);

	@Autowired
	private ConcurrentProcessor processor;

	@PostMapping(value = "/process")
	@ResponseBody
	public ResponseEntity<?> process(@RequestBody final ProcessRequest request) {

		try {
			processor.handle(request);
			return ResponseEntity.noContent().build();

		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{ \"error\": \"" + e.getMessage() + "\"");
		}

	}

}
