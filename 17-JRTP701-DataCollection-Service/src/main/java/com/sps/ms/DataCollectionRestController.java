package com.sps.ms;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sps.binding.EducationInputs;
import com.sps.binding.IncomeInputs;
import com.sps.binding.PlanOutput;
import com.sps.binding.SelectPlanInput;
import com.sps.service.IDcMgmtService;

@RestController
@RequestMapping("/dataCollection-api")
public class DataCollectionRestController {

	private IDcMgmtService service;

	public DataCollectionRestController(IDcMgmtService service) {
		this.service = service;
	}

	@GetMapping("/createId/{appId}")
	public ResponseEntity<Integer> createCaseNo(@PathVariable Integer appId) {
		int caseNo = service.generateCaseNo(appId);
		return new ResponseEntity<>(caseNo, HttpStatus.OK);
	}

	@GetMapping("/getAllPlanNam")
	public ResponseEntity<List<PlanOutput>> getAllPlanName() {
		List<PlanOutput> planDetails = service.showAllPlanName();
		return new ResponseEntity<>(planDetails, HttpStatus.OK);
	}

	@PutMapping("/selectPlan")
	public ResponseEntity<Integer> selectPlan(@RequestBody SelectPlanInput planDetail) {
		int selectedCaseNo = service.savePlanSelection(planDetail);
		return new ResponseEntity<>(selectedCaseNo, HttpStatus.OK);
	}

	/*@PostMapping("/savePlan")
	private ResponseEntity<String> savePlan(@RequestBody List<PlanInput> input) {
	    try {
	        String msg = service.savePlan(input);
	        return new ResponseEntity<>(msg, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}*/

	@PostMapping("/saveIncome")
	public ResponseEntity<Integer> saveIncome(@RequestBody IncomeInputs incomeData) {
		int caseNo = service.saveIncomeDetails(incomeData);
		return new ResponseEntity<>(caseNo, HttpStatus.OK);
	}

	@PostMapping("/saveEducation")
	public ResponseEntity<Integer> saveEducation(@RequestBody EducationInputs educationData) {
		int caseNo = service.saveEducationDetails(educationData);
		return new ResponseEntity<>(caseNo, HttpStatus.OK);
	}

}
