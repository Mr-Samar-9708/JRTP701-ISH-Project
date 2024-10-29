package com.sps.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sps.data.binding.EligibilityDeterminationOutput;
import com.sps.entity.CitizenAppRegistrationEntity;
import com.sps.entity.DcCaseEntity;
import com.sps.entity.DcChildrenEntity;
import com.sps.entity.DcEducationEntity;
import com.sps.entity.DcIncomeEntity;
import com.sps.entity.DcPlanEntity;
import com.sps.entity.EligibilityDeterminationEntity;
import com.sps.repository.EligibilityDeterminationRepo;
import com.sps.repository.ICitizenAppRegistrationRepository;
import com.sps.repository.IDcCaseRepository;
import com.sps.repository.IDcChildrenRepository;
import com.sps.repository.IDcEducationRepository;
import com.sps.repository.IDcIncomeRepository;
import com.sps.repository.IDcPlanRepository;

import jakarta.annotation.Nonnull;

@Service
public class EligibilityDeterminationMngServImpl implements IEligibilityDeterminationMngService {

	private EligibilityDeterminationRepo eligibilityRepo;
	private ICitizenAppRegistrationRepository citizenRepo;
	private IDcCaseRepository caseRepo;
	private IDcChildrenRepository childrenRepo;
	private IDcEducationRepository educationRepo;
	private IDcIncomeRepository incomeRepo;
	private IDcPlanRepository planRepo;

	// Plan Constant
	private static final String PLAN_SNAP = "SNAP";
	private static final String PLAN_CCAP = "CCAP";
	private static final String PLAN_MEDAID = "MEDAID";
	private static final String PLAN_MEDCARE = "MEDCARE";
	private static final String PLAN_CAJW = "CAJW";
	private static final String PLAN_QHP = "QHP";
	private static final String PLAN_APPROVED = "Approved";
	private static final String PLAN_DENIED = "Denied";

	// Constructor Injection for initializing 
	public EligibilityDeterminationMngServImpl(EligibilityDeterminationRepo eligibilityRepo,
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
	public EligibilityDeterminationOutput determineEligibility(@Nonnull Integer caseNo) {
		EligibilityDeterminationOutput output = null;
		EligibilityDeterminationEntity entity = new EligibilityDeterminationEntity();

		DcCaseEntity caseEntity = caseRepo.findById(caseNo).orElseThrow(() -> new RuntimeException("Case not found"));

		DcPlanEntity planEntity = planRepo.findById(caseEntity.getPlainId()).orElseThrow(() -> new RuntimeException("Plan not found"));

		CitizenAppRegistrationEntity citizenEntity = citizenRepo.findById(caseEntity.getAppId()).orElseThrow(() -> new RuntimeException("Citizen not found"));

		String planName = planEntity.getPlanName();
		LocalDate currentDate = LocalDate.now();
		int citizenAge = Period.between(citizenEntity.getDob(), currentDate).getYears();

		output = applyPlanCondition(caseNo, planName, citizenAge);
		output.setCaseNo(caseNo);
		output.setHolderName(citizenEntity.getFullName());
		output.setHolderSSN(citizenEntity.getSsn());

		BeanUtils.copyProperties(output, entity);
		eligibilityRepo.save(entity);

		return output;
	}

	// Helper Method
	private EligibilityDeterminationOutput applyPlanCondition(Integer caseNo, String planName, int citizenAge) {
		EligibilityDeterminationOutput eligibilityOutput = new EligibilityDeterminationOutput();

		DcIncomeEntity income = incomeRepo.findByCaseNo(caseNo);
		double empIncome = income.getEmpIncome();
		double propertyIncome = income.getPropertyIncome();

		if (planName.equalsIgnoreCase(PLAN_SNAP)) {
			if (empIncome <= 300) {
				eligibilityOutput.setPlanStatus(PLAN_APPROVED);
				eligibilityOutput.setBenifitAmt(200.0);
			} else {
				eligibilityOutput.setPlanStatus(PLAN_DENIED);
				eligibilityOutput.setDenialReason("SNAP rules are not statisfied.");
			}
		} // SNAP
		else if (planName.equalsIgnoreCase(PLAN_CCAP)) {
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
				eligibilityOutput.setDenialReason(PLAN_APPROVED);
				eligibilityOutput.setBenifitAmt(300.0);
			} else {
				eligibilityOutput.setPlanStatus(PLAN_DENIED);
				eligibilityOutput.setDenialReason("CCAP rules are not statisfied.");
			}
		} // CCAP
		else if (planName.equalsIgnoreCase(PLAN_MEDAID)) {
			if (empIncome <= 300 && propertyIncome == 0) {
				eligibilityOutput.setPlanStatus(PLAN_APPROVED);
				eligibilityOutput.setBenifitAmt(200.0);
			} else {
				eligibilityOutput.setPlanStatus(PLAN_DENIED);
				eligibilityOutput.setDenialReason("MEDAID rules are not statisfied.");
			}
		} // MEDIAD
		else if (planName.equalsIgnoreCase(PLAN_MEDCARE)) {

			if (citizenAge >= 65) {
				eligibilityOutput.setPlanStatus(PLAN_APPROVED);
				eligibilityOutput.setBenifitAmt(350.0);
			} else {
				eligibilityOutput.setPlanStatus(PLAN_DENIED);
				eligibilityOutput.setDenialReason("MEDCARE rules are not statisfied.");
			} // else

		} // MEDCARE
		else if (planName.equalsIgnoreCase(PLAN_CAJW)) {
			DcEducationEntity educationEntity = educationRepo.findByCaseNo(caseNo);
			int passOutYear = educationEntity.getPassOutYear().getYear();

			if (empIncome == 0 && passOutYear < LocalDate.now().getYear()) {
				eligibilityOutput.setPlanStatus(PLAN_APPROVED);
				eligibilityOutput.setBenifitAmt(300.0);
			} else {
				eligibilityOutput.setPlanStatus(PLAN_DENIED);
				eligibilityOutput.setDenialReason("CAJW rules are not statisfied.");
			} // else
		} // CAJW
		else if (planName.equalsIgnoreCase(PLAN_QHP)) {
			if (citizenAge > 1) {
				eligibilityOutput.setPlanStatus(PLAN_APPROVED);
			} else {
				eligibilityOutput.setPlanStatus(PLAN_DENIED);
				eligibilityOutput.setDenialReason("QHP rules are not statisfied");
			}
		}

		// Checking plan status is 'Approved' or not
		if (eligibilityOutput.getPlanStatus().equalsIgnoreCase(PLAN_APPROVED)) {
			eligibilityOutput.setPlanStartDate(LocalDate.now());
			eligibilityOutput.setPlanEndDate(LocalDate.now().plusYears(2));
		}

		return eligibilityOutput;
	}

}
