package com.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sps.entity.DcIncomeEntity;

public interface IDcIncomeRepository extends JpaRepository<DcIncomeEntity, Integer> {

	public DcIncomeEntity findByCaseNo(Integer caseNo);

}
