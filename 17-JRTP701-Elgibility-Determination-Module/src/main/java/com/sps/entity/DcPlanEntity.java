package com.sps.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "JRTP701_DC_Plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class DcPlanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer planId;
	@NonNull
	private Integer caseNo;
	@NonNull
	@Column(length = 32)
	private String planName;
	@NonNull
	// @JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate startDate;
	@NonNull
	// @JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate endData;
	@NonNull
	@Column(length = 200)
	private String description;
	@NonNull
	@Column(length = 12)
	private String activeSw;

	// Meta-Data
	@Column(length = 30)
	private String createdBy;
	@Column(length = 30)
	private String updatedBy;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime creationDate;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updationDate;
}
