package com.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sps.entity.DcPlanEntity;

public interface IDcPlanRepository extends JpaRepository<DcPlanEntity, Integer> {

	public DcPlanEntity findByPlanName(String planName);
}
