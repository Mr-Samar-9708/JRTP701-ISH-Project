package com.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sps.entity.DcEducationEntity;

public interface IDcEducationRepository extends JpaRepository<DcEducationEntity, Integer> {
	
	public DcEducationEntity findByCaseNo(Integer caseNo);
}
