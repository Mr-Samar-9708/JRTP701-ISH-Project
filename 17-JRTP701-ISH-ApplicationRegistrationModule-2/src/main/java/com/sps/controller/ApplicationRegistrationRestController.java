package com.sps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sps.bindigData.AppRegistrationInput;
import com.sps.service.CitizenApplicationRegistrationImpl;

@RestController
@RequestMapping("/AR-module-api")
public class ApplicationRegistrationRestController {

	@Autowired
	private CitizenApplicationRegistrationImpl appService;

	@PostMapping("/saveCitizen")
	public ResponseEntity<String> saveCitizenApplication(@RequestBody AppRegistrationInput data) {
		try {
			int saveAppId = appService.citizenRegistration(data);
			if (saveAppId > 0) {
				return new ResponseEntity<>("You're successfully registred. App Id :: " + saveAppId, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Invalid SSN and Citizen must belong to California", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
