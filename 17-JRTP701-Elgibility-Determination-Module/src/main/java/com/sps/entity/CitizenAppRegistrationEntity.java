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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "JRTP701_Citizen_AR_module")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CitizenAppRegistrationEntity {

	@Id
	@SequenceGenerator(name = "gen1_seq", sequenceName = "app_id_seq", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "gen1_seq", strategy = GenerationType.SEQUENCE)
	private Integer appId;
	@NonNull
	@Column(length = 31)
	private String fullName;
	@NonNull
	@Column(length = 30)
	private String email;
	@NonNull
	private Long phoneNo;
	@NonNull
	private Integer ssn;
	@NonNull
	@Column(length = 32)
	private String stateName;
	@NonNull
	private LocalDate dob;
	@NonNull
	@Column(length = 22)
	private String createdBy;
	@NonNull
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
