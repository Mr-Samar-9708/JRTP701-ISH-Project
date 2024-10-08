package com.sps.bindigData;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppRegistrationInput {

	private String fullName;
	private String email;
	private Long phoneNo;
	private Integer ssn;
	private LocalDate dob;
}
