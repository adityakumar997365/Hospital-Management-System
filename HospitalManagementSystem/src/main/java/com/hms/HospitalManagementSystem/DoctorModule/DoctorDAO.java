package com.hms.HospitalManagementSystem.DoctorModule;

import java.util.List;

public interface DoctorDAO {

	List<DoctorModel> getAllDoctor();
	
	DoctorModel addNewDoctor(DoctorDTO dto);
	
	DoctorModel getUserByContact(String usernameMob);
	
	 boolean existsByContactNumber(String contactNumber);
}
