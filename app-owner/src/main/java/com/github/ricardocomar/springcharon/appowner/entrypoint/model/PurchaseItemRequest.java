package com.github.ricardocomar.springcharon.appowner.entrypoint.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseItemRequest {
	private String id;
	private String sku;
	private String title;
	private BigDecimal value;

	private Integer waitMin;
	private Integer waitMax;
}
