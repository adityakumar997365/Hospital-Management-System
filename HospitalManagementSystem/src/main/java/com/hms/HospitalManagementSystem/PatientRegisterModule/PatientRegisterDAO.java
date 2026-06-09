package com.hms.HospitalManagementSystem.PatientRegisterModule;

import java.util.List;

public interface PatientRegisterDAO {
	
	PatientRegisterModel registerNewPatient(PatientRegisterDTO dto);
	
	List<PatientRegisterModel> findAllRegisterPatient();
	
	PatientRegisterModel checkStatusOfApproval(String contactNumber);
	
	public void approveRegistration(Integer registerId);
	
	public void rejectRegistration(Integer registerId, String reason);

}
