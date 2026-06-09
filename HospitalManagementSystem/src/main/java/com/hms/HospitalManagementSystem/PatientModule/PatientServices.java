package com.hms.HospitalManagementSystem.PatientModule;

import java.util.List;

public interface PatientServices {

	List<PatientModel> getAllPatient();
	
	PatientModel getUserByContact(String contact);
	
	PatientModel getPatientById(Integer patinetid);

}
