package com.hms.HospitalManagementSystem.AdminModule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<AdminModel, Integer> {

	AdminModel findByName(String username);
	
	Optional<AdminModel> findByContactNumber(String contact);
}
