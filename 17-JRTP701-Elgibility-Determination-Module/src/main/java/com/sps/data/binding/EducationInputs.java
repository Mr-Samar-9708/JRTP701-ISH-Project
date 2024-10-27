package com.sps.data.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EducationInputs {

	private Integer caseNo;
	private String highestQly;
	private LocalDate passOutYear;
}
