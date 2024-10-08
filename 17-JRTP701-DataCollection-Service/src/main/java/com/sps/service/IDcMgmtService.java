package com.sps.service;

import java.util.List;

import com.sps.binding.ChildInputs;
import com.sps.binding.DcSummaryReport;
import com.sps.binding.EducationInputs;
import com.sps.binding.IncomeInputs;
import com.sps.binding.PlanSelectionInputs;

public interface IDcMgmtService {

	public Integer generateCaseNo(Integer appId);

	public List<String> showAllPlanName();

	public Integer savePlanSelection(PlanSelectionInputs planDetail);
	
	public Integer saveIncomeDetails(IncomeInputs incomeData);
	
	public Integer saveEducationDetails(EducationInputs educationData);
	
	public Integer saveChilderenDetails(List<ChildInputs> childData);
	
	public DcSummaryReport showDCSummary(Integer caseNo);
}
