package com.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sps.entity.CitizenAppRegistrationEntity;

public interface ICitizenAppRegistrationRepository extends JpaRepository<CitizenAppRegistrationEntity, Integer> {

}
