package com.sps.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "JRTP701_DC_Childeren")
@Data
public class DcChildrenEntity {

	@Id
	/*@SequenceGenerator(name = "seq_gen1", sequenceName = "gen_childrenId", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "seq_gen1", strategy = GenerationType.SEQUENCE)*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer childId;
	private Integer caseNo;
	private LocalDate childDOB;
	private Long childSSN;
}
