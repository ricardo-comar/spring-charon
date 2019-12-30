package com.github.ricardocomar.springcharon.appowner.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(nullable = false)
	private String customer;

	@Column(nullable = false, unique = true)
	private String requestId;

	@Column(nullable = false)
	private BigDecimal totalValue;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PurchaseStatus status;

	@Column(nullable = false)
	private LocalDateTime date;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseItemEntity> items;

	public enum PurchaseStatus {
		OPEN, PAID, CANCELLED, RETURNED, DELIVERED;
	}


}