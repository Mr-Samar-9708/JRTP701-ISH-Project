package com.sps.ms;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sps.data.binding.EligibilityDeterminationOutput;
import com.sps.service.IEligibilityDeterminationMngService;

@RestController
@RequestMapping("/eligibilityDetermination-api")
public class EligibilityDeterminationController {

	private IEligibilityDeterminationMngService service;

	@GetMapping("/checkByCaseNo/{caseNo}")
	public ResponseEntity<EligibilityDeterminationOutput> checkEligibilityByCaseNo(@PathVariable int caseNo) {
		EligibilityDeterminationOutput output = service.determineEligibility(caseNo);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}
