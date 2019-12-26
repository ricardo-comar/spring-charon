package com.github.ricardocomar.springcharon.appowner.entrypoint.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessRequest {

	private String id;
	private String payload;

	private Integer waitMin;
	private Integer waitMax;
}
