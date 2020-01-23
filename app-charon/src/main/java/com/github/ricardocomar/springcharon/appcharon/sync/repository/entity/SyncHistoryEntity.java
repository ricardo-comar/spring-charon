package com.github.ricardocomar.springcharon.appcharon.sync.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sync_history_charon")
public class SyncHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	// TODO one-to-many

	// TODO composite key

	@Column(unique = true, nullable = false, updatable = false)
	private String syncId;

	@Column(nullable = false, updatable = false)
	private Integer sequence;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SyncState state;

	@Column(nullable = false, updatable = false)
	private LocalDateTime dateTime;

	public enum SyncState {
		OK, INVALID, DISCARDED;
	}

}
