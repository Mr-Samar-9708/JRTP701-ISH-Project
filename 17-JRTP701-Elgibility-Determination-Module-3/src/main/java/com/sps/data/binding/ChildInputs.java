package com.sps.data.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ChildInputs {

	private Integer caseNo;
	private LocalDate childDOB;
	private Long childSSN;
}
