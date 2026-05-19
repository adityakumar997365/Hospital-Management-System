package com.hms.HospitalManagementSystem.PatientModule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientModel, Integer> {
	
	Optional<PatientModel> findByContactNumber(String contact);

}
