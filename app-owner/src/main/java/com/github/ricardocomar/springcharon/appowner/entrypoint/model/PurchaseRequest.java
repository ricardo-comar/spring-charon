package com.github.ricardocomar.springcharon.appowner.entrypoint.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseRequest {

	private String id;
	private String customer;
	private String status;
	private LocalDateTime date;

	private Integer waitMin;
	private Integer waitMax;
}
