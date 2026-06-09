package com.hms.HospitalManagementSystem.PatientRegisterModule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRegisterRepository extends JpaRepository<PatientRegisterModel, Integer>{

	Optional<PatientRegisterModel> findByContactNumber(String contactNumber);
	
	//check existence for new registration
	 boolean existsByContactNumber(String contactNumber);
	
}
