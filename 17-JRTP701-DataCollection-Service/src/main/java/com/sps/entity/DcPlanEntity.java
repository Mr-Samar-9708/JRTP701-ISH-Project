package com.sps.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "JRTP701_DC_Plan")
@Data
public class DcPlanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer planId;
	private Integer caseNo;
	@Column(length = 32)
	private String planName;
	private LocalDate startDate;
	private LocalDate endData;
	@Column(length = 200)
	private String description;
	@Column(length = 12)
	private String activeSw;

	// Meta-Data
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime creationDate;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updationDate;
	@Column(length = 30)
	private String createdBy;
	@Column(length = 30)
	private String updatedBy;
}
