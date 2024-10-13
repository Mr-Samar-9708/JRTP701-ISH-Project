package com.sps.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "JRTP701_Eligibility_Determination")
public class EligibilityDeterminationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer edTraceId;
	private Integer caseNo;
	@Column(length = 30)
	private String holderName;
	private Integer holderSSN;
	@Column(length = 25)
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private String denialReason;

}
