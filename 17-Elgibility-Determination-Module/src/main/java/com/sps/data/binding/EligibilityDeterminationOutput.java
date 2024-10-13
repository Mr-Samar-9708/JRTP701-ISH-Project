package com.sps.data.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EligibilityDeterminationOutput {

	private Integer caseNo;
	private String holderName;
	private Integer holderSSN;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private String denialReason;
}
