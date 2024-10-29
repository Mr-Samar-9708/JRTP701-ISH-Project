package com.sps.binding;

import java.util.List;

import com.sps.entity.DcCaseEntity;

import lombok.Data;

@Data
public class DcSummaryReport {

	private CitizenAppRegistrationInput citizenRegistration;
	private DcCaseEntity caseDetails;
	private String planName;
	private EducationInputs educationInputs;
	private List<ChildInputs> childDetails;
	private IncomeInputs incomeDetails;
}
