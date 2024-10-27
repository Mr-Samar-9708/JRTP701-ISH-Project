package com.sps.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "JRTP701_DC_CASE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class DcCaseEntity {

	@Id
	/*@SequenceGenerator(name = "seq_gen1", sequenceName = "gen_caseNumber", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "seq_gen1", strategy = GenerationType.SEQUENCE)*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer caseNo;
	@NonNull
	private Integer appId;
	@NonNull
	private Integer plainId;

}
