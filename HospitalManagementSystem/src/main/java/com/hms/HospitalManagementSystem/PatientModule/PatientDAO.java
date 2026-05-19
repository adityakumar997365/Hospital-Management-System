package com.hms.HospitalManagementSystem.PatientModule;

import java.util.List;

public interface PatientDAO {
	
	List<PatientModel> getAllPatient();
	
	PatientModel getUserByContact(String contact);
	

}
