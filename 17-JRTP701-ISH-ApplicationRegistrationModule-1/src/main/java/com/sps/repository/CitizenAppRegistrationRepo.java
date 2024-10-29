package com.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sps.entity.CitizenAppRegistrationEntity;

public interface CitizenAppRegistrationRepo extends JpaRepository<CitizenAppRegistrationEntity, Integer> {

}
