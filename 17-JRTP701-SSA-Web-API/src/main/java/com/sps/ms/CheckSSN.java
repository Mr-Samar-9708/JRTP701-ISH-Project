package com.sps.ms;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssa-web-api")
public class CheckSSN {

	private String stateName;

	@GetMapping("/getState/{ssn}")
	public ResponseEntity<String> ssnIsCalifornia(@PathVariable Integer ssn) {

		String ssnNumber = String.valueOf(ssn);

		if (StringUtils.hasLength(ssnNumber) && ssnNumber.length() == 9) {
			// Calculating Social Security Number for State code
			int stateCode = ssn % 100;

			if (stateCode == 01) {
				stateName = "Washington";
			} else if (stateCode == 02) {
				stateName = "California";
			} else if (stateCode == 03) {
				stateName = "Massachusetts";
			} else if (stateCode == 04) {
				stateName = "Colorado";
			} else if (stateCode == 05) {
				stateName = "Colorado";
			} else if (stateCode == 06) {
				stateName = "St. Louis";
			} else if (stateCode == 07) {
				stateName = "Utah";
			} /*else if (stateCode == 08) {
				stateName = "Vermont";
				}*/
		}
		return new ResponseEntity<>(stateName, HttpStatus.OK);
	}
}
