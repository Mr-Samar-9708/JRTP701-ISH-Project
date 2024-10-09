package com.sps.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanInput {

	private Integer planId;
	private String planName;
	private LocalDate startDate;
	private LocalDate endData;
	private String description;
	private String activeSw;
}
