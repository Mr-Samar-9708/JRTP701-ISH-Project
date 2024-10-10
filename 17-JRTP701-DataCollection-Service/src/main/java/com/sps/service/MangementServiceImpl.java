package com.sps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sps.binding.ChildInputs;
import com.sps.binding.CitizenAppRegistrationInput;
import com.sps.binding.DcSummaryReport;
import com.sps.binding.EducationInputs;
import com.sps.binding.IncomeInputs;
import com.sps.binding.PlanInput;
import com.sps.binding.PlanOutput;
import com.sps.binding.SelectPlanInput;
import com.sps.entity.CitizenAppRegistrationEntity;
import com.sps.entity.DcCaseEntity;
import com.sps.entity.DcChildrenEntity;
import com.sps.entity.DcEducationEntity;
import com.sps.entity.DcIncomeEntity;
import com.sps.entity.DcPlanEntity;
import com.sps.repository.ICitizenAppRegistrationRepository;
import com.sps.repository.IDcCaseRepository;
import com.sps.repository.IDcChildrenRepository;
import com.sps.repository.IDcEducationRepository;
import com.sps.repository.IDcIncomeRepository;
import com.sps.repository.IDcPlanRepository;

@Service
public class MangementServiceImpl implements IDcMgmtService {

	private ICitizenAppRegistrationRepository appRegsRepo;
	private IDcCaseRepository caseRepo;
	private IDcPlanRepository planRepo;
	private IDcIncomeRepository incomeRepo;
	private IDcEducationRepository educationRepo;
	private IDcChildrenRepository childRepo;

	public MangementServiceImpl(ICitizenAppRegistrationRepository appRegsRepo, IDcCaseRepository caseRepo,
			IDcPlanRepository planRepo, IDcIncomeRepository incomeRepo, IDcEducationRepository educationRepo,
			IDcChildrenRepository childRepo) {
		this.appRegsRepo = appRegsRepo;
		this.caseRepo = caseRepo;
		this.planRepo = planRepo;
		this.incomeRepo = incomeRepo;
		this.educationRepo = educationRepo;
		this.childRepo = childRepo;
	}

	@Override
	public Integer generateCaseNo(Integer appId) {
		Optional<CitizenAppRegistrationEntity> opt = appRegsRepo.findById(appId);
		if (opt.isPresent()) {
			int existAppId = opt.get().getAppId();
			DcCaseEntity caseEntity = new DcCaseEntity();
			caseEntity.setAppId(existAppId);
			return caseRepo.save(caseEntity).getCaseNo();
		}
		return 0;
	}

	@Override
	public List<PlanOutput> showAllPlanName() {
		List<DcPlanEntity> allPlan = planRepo.findAll();
		// Here we are using method reference
		// return allPlan.stream().map(DcPlanEntity::getPlanName).toList();
		List<PlanOutput> listOfPlan = new ArrayList<>();
		allPlan.forEach(plan -> {
			PlanOutput output = new PlanOutput();
			BeanUtils.copyProperties(plan, output);
			listOfPlan.add(output);
		});
		return listOfPlan;
	}

	@Override
	public Integer savePlanSelection(SelectPlanInput planDetail) {
		// finding caseNo
		Optional<DcCaseEntity> opt = caseRepo.findById(planDetail.getCaseNo());
		if (opt.isPresent()) {
			DcCaseEntity existCase = opt.get();
			existCase.setPlainId(planDetail.getPlanId());
			existCase.setAppId(planDetail.getAppId());
			// Updating DcCaseEntity
			return caseRepo.save(existCase).getCaseNo();
		}
		return 0;
	}

	@Override
	public Integer saveIncomeDetails(IncomeInputs incomeData) {
		// Target Object
		DcIncomeEntity incomeEntity = new DcIncomeEntity();
		// Accessing data from resource
		BeanUtils.copyProperties(incomeData, incomeEntity);
		// saving data
		DcIncomeEntity savedIncome = incomeRepo.save(incomeEntity);
		// returning caseNo
		return savedIncome.getCaseNo();
	}

	@Override
	public Integer saveEducationDetails(EducationInputs educationData) {
	    DcEducationEntity eductSaved = null;
	    DcEducationEntity obj = educationRepo.findByCaseNo(educationData.getCaseNo());

	    if (obj != null) {
	        BeanUtils.copyProperties(educationData, obj);
	        eductSaved = educationRepo.save(obj); // Save the updated object
	    } else {
	        // Target Object
	        DcEducationEntity educationObj = new DcEducationEntity();
	        // Accessing data from resource
	        BeanUtils.copyProperties(educationData, educationObj);
	        // Saving data
	        eductSaved = educationRepo.save(educationObj);
	    }

	    // If eductSaved is null, throw an exception
	    if (eductSaved == null) {
	        throw new IllegalStateException("Failed to save education details.");
	    }
	    
	    return eductSaved.getCaseNo();
	}


	@Override
	public Integer saveChilderenDetails(List<ChildInputs> childData) {
		childData.forEach(child -> {
			// Target Object
			DcChildrenEntity childEntity = new DcChildrenEntity();
			// Accessing data from resource
			BeanUtils.copyProperties(childData, child);
			// Saving data
			childRepo.save(childEntity);
		});
		// returning caseNo
		return childData.get(0).getCaseNo();
	}

	@Override
	public DcSummaryReport showDCSummary(Integer caseNo) {
		// Fetching all data based on CaseNo
		List<DcChildrenEntity> childEntity = childRepo.findByCaseNo(caseNo);
		DcEducationEntity educationEntity = educationRepo.findByCaseNo(caseNo);
		DcIncomeEntity incomeEntity = incomeRepo.findByCaseNo(caseNo);
		Optional<DcCaseEntity> caseEntity = caseRepo.findById(caseNo);

		// We are getting ready our binding class object for store data
		CitizenAppRegistrationInput citizenInput = new CitizenAppRegistrationInput();
		DcCaseEntity caseDetails = null;
		EducationInputs educationInputs = new EducationInputs();
		List<ChildInputs> childDetails = new ArrayList<>();
		IncomeInputs incomeDetails = new IncomeInputs();

		String plainNam = null;
		if (caseEntity.isPresent()) {
			/*After accessing CaseEntity class object from Optional class we are injecting actual DcCaseEntity object */
			caseDetails = caseEntity.get();
			int plainId = caseDetails.getPlainId();
			// fetching DcPlainEntity object by using plain i'd
			Optional<DcPlanEntity> plainEntity = planRepo.findById(plainId);
			if (plainEntity.isPresent()) {
				plainNam = plainEntity.get().getPlanName();
			}
		}

		Optional<CitizenAppRegistrationEntity> citizenEntity = appRegsRepo.findById(caseDetails.getAppId());
		if (citizenEntity.isPresent()) {
			BeanUtils.copyProperties(citizenEntity.get(), citizenInput);
		}

		BeanUtils.copyProperties(educationEntity, educationInputs);
		BeanUtils.copyProperties(incomeEntity, incomeDetails);
		childEntity.forEach(ch -> {
			ChildInputs chInput = new ChildInputs();
			BeanUtils.copyProperties(ch, chInput);
			childDetails.add(chInput);
		});

		DcSummaryReport report = new DcSummaryReport();
		report.setPlanName(plainNam);
		report.setCitizenRegistration(citizenInput);
		report.setIncomeDetails(incomeDetails);
		report.setCaseDetails(caseDetails);
		report.setEducationInputs(educationInputs);
		report.setChildDetails(childDetails);
		return report;
	}

	@Override
	public String savePlan(List<PlanInput> input) {
		List<DcPlanEntity> listOfPlan = new ArrayList<>();
		input.forEach(plan -> {
			DcPlanEntity planEntity = new DcPlanEntity();
			BeanUtils.copyProperties(plan, planEntity);
			listOfPlan.add(planEntity);
		});
		List<DcPlanEntity> saved = planRepo.saveAll(listOfPlan);
		return !saved.isEmpty() ? "Plan has saved..!" : "Plan has not saved..!";
	}

}
