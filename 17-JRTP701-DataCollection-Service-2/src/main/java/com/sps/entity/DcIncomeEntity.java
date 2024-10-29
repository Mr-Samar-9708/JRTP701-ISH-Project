package com.sps.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "JRTP701_DC_Income")
@Data
public class DcIncomeEntity {

	@Id
	@SequenceGenerator(name = "seq_gen1", sequenceName = "gen_incomeId", initialValue = 969, allocationSize = 1)
	@GeneratedValue(generator = "seq_gen1", strategy = GenerationType.SEQUENCE)
	private Integer incomeId;
	private Integer caseNo;
	private Double empIncome;
	private Double propertyIncome;
}
