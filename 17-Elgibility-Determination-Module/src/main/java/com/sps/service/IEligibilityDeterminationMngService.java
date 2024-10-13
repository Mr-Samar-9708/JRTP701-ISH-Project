package com.sps.service;

import com.sps.data.binding.EligibilityDeterminationOutput;

public interface IEligibilityDeterminationMngService {

	EligibilityDeterminationOutput determineEligibility(Integer caseNo);

	EligibilityDeterminationOutput applyPlanCondition(Integer caseNo, String planName, int citizenAge);
}
