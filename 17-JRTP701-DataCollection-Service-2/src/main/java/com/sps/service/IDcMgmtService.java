package com.sps.service;

import java.util.List;

import com.sps.binding.ChildInputs;
import com.sps.binding.DcSummaryReport;
import com.sps.binding.EducationInputs;
import com.sps.binding.IncomeInputs;
import com.sps.binding.PlanInput;
import com.sps.binding.PlanOutput;
import com.sps.binding.SelectPlanInput;

public interface IDcMgmtService {

	public Integer generateCaseNo(Integer appId);

	public List<PlanOutput> showAllPlanName();

	public String savePlan(List<PlanInput> input);

	public Integer savePlanSelection(SelectPlanInput planDetail);

	public Integer saveIncomeDetails(IncomeInputs incomeData);

	public Integer saveEducationDetails(EducationInputs educationData);

	public Integer saveChilderenDetails(List<ChildInputs> childData);

	public DcSummaryReport showDCSummary(Integer caseNo);
}
