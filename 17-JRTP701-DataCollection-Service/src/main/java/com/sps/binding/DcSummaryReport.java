package com.sps.binding;

import java.util.List;

import com.sps.entity.DcCaseEntity;
import com.sps.entity.DcPlanEntity;

import lombok.Data;

@Data
public class DcSummaryReport {

	private CitizenAppRegistrationInput citizenRegistration;
	private DcCaseEntity caseDetails;
	private DcPlanEntity planEntity;
	private EducationInputs educationInputs;
	private List<ChildInputs> childDetails;
	private IncomeInputs incomeDetails;
}
