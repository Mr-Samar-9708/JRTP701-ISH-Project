package com.sps.service;

import org.springframework.stereotype.Service;

import com.sps.data.binding.EligibilityDeterminationOutput;
import com.sps.entity.EligibilityDeterminationEntity;
import com.sps.repository.EligibilityDeterminationRepo;
import com.sps.repository.ICitizenAppRegistrationRepository;
import com.sps.repository.IDcCaseRepository;
import com.sps.repository.IDcChildrenRepository;
import com.sps.repository.IDcEducationRepository;
import com.sps.repository.IDcIncomeRepository;
import com.sps.repository.IDcPlanRepository;

@Service
public class EligibilityDeterminationMngServ implements IEligibilityDeterminationMngService {

	private EligibilityDeterminationRepo eligibilityRepo;
	private ICitizenAppRegistrationRepository citizenRepo;
	private IDcCaseRepository caseRepo;
	private IDcChildrenRepository childrenRepo;
	private IDcEducationRepository educationRepo;
	private IDcIncomeRepository incomeRepo;
	private IDcPlanRepository planRepo;

	public EligibilityDeterminationMngServ(EligibilityDeterminationRepo eligibilityRepo,
			ICitizenAppRegistrationRepository citizenRepo, IDcCaseRepository caseRepo,
			IDcChildrenRepository childrenRepo, IDcEducationRepository educationRepo, IDcIncomeRepository incomeRepo,
			IDcPlanRepository planRepo) {

		this.eligibilityRepo = eligibilityRepo;
		this.citizenRepo = citizenRepo;
		this.caseRepo = caseRepo;
		this.childrenRepo = childrenRepo;
		this.educationRepo = educationRepo;
		this.incomeRepo = incomeRepo;
		this.planRepo = planRepo;
	}

	@Override
	public EligibilityDeterminationOutput determineEligibility(Integer caseNo) {
		// For save eligible person data
		EligibilityDeterminationEntity entity = new EligibilityDeterminationEntity();

		// For return final Result
		EligibilityDeterminationOutput output = new EligibilityDeterminationOutput();

		return null;
	}

	@Override
	public EligibilityDeterminationOutput applyPlanCondition(Integer caseNo, String planName, int citizenAge) {

		return null;
	}

}
