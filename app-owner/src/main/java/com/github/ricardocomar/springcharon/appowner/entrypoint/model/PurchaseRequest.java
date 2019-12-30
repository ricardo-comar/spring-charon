package com.github.ricardocomar.springcharon.appowner.entrypoint.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseRequest {

	private String id;
	private String customer;
	private BigDecimal totalValue;
	private String status;
	private LocalDate date;

	private Integer waitMin;
	private Integer waitMax;
}
