package com.hms.HospitalManagementSystem.PatientRegisterModule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientRegisterServicesImpl implements PatientRegisterServices {

	@Autowired
	private PatientRegisterDAO patientRegisterDAO;
	 
	@Override
	public PatientRegisterModel registerNewPatient(PatientRegisterDTO dto) {
		return patientRegisterDAO.registerNewPatient(dto);
	}

	@Override
	public List<PatientRegisterModel> findAllRegisterPatient() {

		
		return patientRegisterDAO.findAllRegisterPatient();
	}

	@Override
	public PatientRegisterModel checkStatusOfApproval(String contactNumber) {

		return patientRegisterDAO.checkStatusOfApproval(contactNumber);
	}

	@Override
	public void approveRegistration(Integer registerId) {

		patientRegisterDAO.approveRegistration(registerId);
	}

	@Override
	public void rejectRegistration(Integer registerId, String reason) {

		patientRegisterDAO.rejectRegistration(registerId, reason);
	}

	 
}

