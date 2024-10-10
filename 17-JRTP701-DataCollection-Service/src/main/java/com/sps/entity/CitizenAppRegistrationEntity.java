package com.sps.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "JRTP701_Citizen_AR_module")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenAppRegistrationEntity {

	@Id
	@SequenceGenerator(name = "gen1_seq", sequenceName = "app_id_seq", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "gen1_seq", strategy = GenerationType.SEQUENCE)
	private Integer appId;
	@Column(length = 31)
	private String fullName;
	@Column(length = 30)
	private String email;
	private Long phoneNo;
	private Integer ssn;
	@Column(length = 32)
	private String stateName;
	private LocalDate dob;
	@Column(length = 22)
	private String createdBy;
	@Column(length = 22)
	private String updatedBy;
	
	// Meta-Data
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createTime;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updateTime;
}
