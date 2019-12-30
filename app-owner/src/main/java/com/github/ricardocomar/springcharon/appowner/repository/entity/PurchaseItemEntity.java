package com.github.ricardocomar.springcharon.appowner.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "purchase_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String requestId;

	@Column(nullable = false)
	private String sku;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private BigDecimal value;

	@ManyToOne(fetch = FetchType.LAZY)
	private PurchaseEntity purchase;

}