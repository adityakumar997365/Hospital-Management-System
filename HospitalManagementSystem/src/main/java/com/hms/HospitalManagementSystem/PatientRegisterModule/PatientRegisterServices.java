package com.hms.HospitalManagementSystem.PatientRegisterModule;

import java.util.List;

public interface PatientRegisterServices {

	public PatientRegisterModel registerNewPatient(PatientRegisterDTO dto);
	
	List<PatientRegisterModel> findAllRegisterPatient();
	
	PatientRegisterModel checkStatusOfApproval(String contactNumber);
	
    //HMS New Patient Approval Request
	public void approveRegistration(Integer registerId);
	
	public void rejectRegistration(Integer registerId, String reason);

}
