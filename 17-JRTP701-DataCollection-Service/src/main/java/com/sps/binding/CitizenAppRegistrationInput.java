package com.sps.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CitizenAppRegistrationInput {

	private Integer appId;
	private String fullName;
	private String email;
	private Long phoneNo;
	private Integer ssn;
	private String stateName;
	private LocalDate dob;
	private String createdBy;
	private String updatedBy;
}