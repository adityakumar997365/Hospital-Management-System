package com.hms.HospitalManagementSystem.DoctorModule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<DoctorModel, Integer> {
	
	Optional<DoctorModel> findByContactNumber(String contact);
	
	Optional<DoctorModel> findByDoctorId(Integer doctorId);

}
