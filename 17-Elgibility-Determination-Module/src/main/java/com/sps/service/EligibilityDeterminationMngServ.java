package com.sps.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sps.data.binding.EligibilityDeterminationOutput;
import com.sps.entity.CitizenAppRegistrationEntity;
import com.sps.entity.DcCaseEntity;
import com.sps.entity.DcChildrenEntity;
import com.sps.entity.DcEducationEntity;
import com.sps.entity.DcIncomeEntity;
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

	private static final String APPROVED = "Approved";
	private static final String DENIED = "Denied";

	@Override
	public EligibilityDeterminationOutput determineEligibility(Integer caseNo) {
		// For save eligible person data
		EligibilityDeterminationEntity entity = new EligibilityDeterminationEntity();

		// For return final Result
		EligibilityDeterminationOutput output = new EligibilityDeterminationOutput();

		return null;
	}

	// Helper Method
	private EligibilityDeterminationOutput applyPlanCondition(Integer caseNo, String planName, int citizenAge) {
		EligibilityDeterminationOutput eligibilityOutput = new EligibilityDeterminationOutput();

		DcIncomeEntity income = incomeRepo.findByCaseNo(caseNo);
		double empIncome = income.getEmpIncome();
		double propertyIncome = income.getPropertyIncome();

		if (planName.equalsIgnoreCase("SNAP")) {
			if (empIncome <= 300) {
				eligibilityOutput.setPlanStatus(APPROVED);
				eligibilityOutput.setBenifitAmt(200.0);
			} else {
				eligibilityOutput.setPlanStatus(DENIED);
				eligibilityOutput.setDenialReason("SNAP rules are not statisfied.");
			}
		} // SNAP
		else if (planName.equalsIgnoreCase("CCAP")) {
			boolean isKidsAvailable = false;
			boolean isKidsAgeApplicable = false;

			List<DcChildrenEntity> childs = childrenRepo.findByCaseNo(caseNo);

			if (!childs.isEmpty()) {
				isKidsAvailable = true;// if kids are available then assigning true..

				for (DcChildrenEntity childObj : childs) {
					int age = Period.between(childObj.getChildDOB(), LocalDate.now()).getYears();
					if (age <= 16) {
						isKidsAgeApplicable = true;// If kids age are less than 16 or equals then setting true..
					} // if
				} // for
			}

			if (empIncome <= 300 && isKidsAvailable && isKidsAgeApplicable) {
				eligibilityOutput.setDenialReason(APPROVED);
				eligibilityOutput.setBenifitAmt(300.0);
			} else {
				eligibilityOutput.setPlanStatus(DENIED);
				eligibilityOutput.setDenialReason("CCAP rules are not statisfied.");
			}
		} // CCAP
		else if (planName.equalsIgnoreCase("MEDAID")) {
			if (empIncome <= 300 && propertyIncome == 0) {
				eligibilityOutput.setPlanStatus(APPROVED);
				eligibilityOutput.setBenifitAmt(200.0);
			} else {
				eligibilityOutput.setPlanStatus(DENIED);
				eligibilityOutput.setDenialReason("MEDAID rules are not statisfied.");
			}
		} // MEDIAD
		else if (planName.equalsIgnoreCase("MEDCARE")) {

			if (citizenAge >= 65) {
				eligibilityOutput.setPlanStatus(APPROVED);
				eligibilityOutput.setBenifitAmt(350.0);
			} else {
				eligibilityOutput.setPlanStatus(DENIED);
				eligibilityOutput.setDenialReason("MEDCARE rules are not statisfied.");
			} // else

		} // MEDCARE
		else if (planName.equalsIgnoreCase("CAJW")) {
			DcEducationEntity educationEntity = educationRepo.findByCaseNo(caseNo);
			int passOutYear = educationEntity.getPassOutYear().getYear();

			if (empIncome == 0 && passOutYear < LocalDate.now().getYear()) {
				eligibilityOutput.setPlanStatus(APPROVED);
				eligibilityOutput.setBenifitAmt(300.0);
			} else {
				eligibilityOutput.setPlanStatus(DENIED);
				eligibilityOutput.setDenialReason("CAJW rules are not statisfied.");
			} // else
		} // CAJW
		else if (planName.equalsIgnoreCase("QHP")) {
			if (citizenAge > 1) {
				eligibilityOutput.setPlanStatus(APPROVED);
			} else {
				eligibilityOutput.setPlanStatus(DENIED);
				eligibilityOutput.setDenialReason("QHP rules are not statisfied");
			}
		}

		// Checking plan status is 'Approved' or not
		if (eligibilityOutput.getPlanStatus().equalsIgnoreCase("Approved")) {
			eligibilityOutput.setPlanStartDate(LocalDate.now());
			eligibilityOutput.setPlanEndDate(LocalDate.now().plusYears(2));
		}

		return eligibilityOutput;
	}

}
