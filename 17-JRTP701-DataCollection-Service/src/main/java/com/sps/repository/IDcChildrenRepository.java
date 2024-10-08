package com.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sps.entity.DcChildrenEntity;

public interface IDcChildrenRepository extends JpaRepository<DcChildrenEntity, Integer> {

	public DcChildrenEntity findByCaseNo(Integer caseNo);
}
