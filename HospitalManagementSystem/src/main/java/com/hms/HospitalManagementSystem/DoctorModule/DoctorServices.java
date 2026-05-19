package com.hms.HospitalManagementSystem.DoctorModule;

import java.util.List;

public interface DoctorServices {
	
	List<DoctorModel> getAllDoctor();

	DoctorModel addNewDoctor(DoctorDTO dto);

	DoctorModel getUserByContact(String usernameMob);
}
